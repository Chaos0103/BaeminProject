package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.delivery.controller.form.PasswordChangeForm;
import project.delivery.controller.form.EmailFindForm;
import project.delivery.controller.form.PasswordFindForm;
import project.delivery.controller.form.LoginForm;
import project.delivery.domain.Member;
import project.delivery.exception.NoSuchException;
import project.delivery.service.LoginService;
import project.delivery.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;

    /**
     * 로그인
     * @Method GET
     */
    @GetMapping
    public String login(@ModelAttribute("loginForm") LoginForm form) {
        return "common/login";
    }

    /**
     * 로그인
     * @Method POST
     * @Valid email, password
     */
    @PostMapping
    public String loginCheck(
            @Validated @ModelAttribute LoginForm form,
            BindingResult bindingResult,
            @RequestParam(defaultValue = "/") String redirectURL,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            return "common/login";
        }

        Member loginMember = loginService.login(form.getEmail(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "계정 정보가 일치하지 않습니다.");
            log.debug("로그인 예외 발생: {}", bindingResult.getTarget());
            return "common/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);
        log.info("회원 번호 {} 로그인", loginMember.getId());
        return "redirect:" + redirectURL;
    }

    /**
     * 로그아웃
     *
     * @Method GET
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        expiredSession(request);
        return "redirect:/";
    }

    /**
     * 이메일 찾기
     *
     * @Method GET
     */
    @GetMapping("/loginId")
    public String findEmail(@ModelAttribute("emailFindForm") EmailFindForm form) {
        return "common/findLoginIdForm";
    }

    /**
     * 이메일 찾기
     *
     * @Method POST
     */
    @PostMapping("/loginId")
    public String findEmailPost(
            @Validated @ModelAttribute EmailFindForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            return "common/findLoginIdForm";
        }

        Member member = loginService.findLoginEmail(form.getPhone());

        if (member == null) {
            bindingResult.reject("nonMember", "등록되지 않은 회원입니다.");
            log.debug("이메일 찾기 예외 발생: {}", bindingResult.getTarget());
            return "common/findLoginIdForm";
        }

        redirectAttributes.addFlashAttribute("email", member.getEmail());
        redirectAttributes.addFlashAttribute("createdDate", member.getCreatedDate());
        log.info("회원번호 {} 아이디 찾기", member.getId());
        return "redirect:/login/loginId/success";
    }

    /**
     * 이메일 찾기 성공
     * @Method GET
     */
    @GetMapping("/loginId/success")
    public String successFindEmail(Model model) {
        String email = (String) model.asMap().get("email");
        LocalDateTime createdDate = (LocalDateTime) model.asMap().get("createdDate");
        model.addAttribute("email", blindEmail(email));
        model.addAttribute("createdDate", createdDate);
        return "common/findLoginId";
    }

    /**
     * 비밀번호 찾기
     * @Method GET
     */
    @GetMapping("/password")
    public String findPassword(@ModelAttribute("passwordFindForm") PasswordFindForm form) {
        return "common/findPasswordForm";
    }

    /**
     * 비밀번호 찾기
     * @Method POST
     */
    @PostMapping("/password")
    public String findPasswordPost(
            @Validated @ModelAttribute PasswordFindForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            return "common/findPasswordForm";
        }

        Long memberId = loginService.findLoginPassword(form.getEmail(), form.getPhone());

        if (memberId == null) {
            bindingResult.reject("nonMember", "등록되지 않은 회원입니다.");
            log.debug("비밀번호 찾기 예외 발생: {}", bindingResult.getTarget());
            return "common/findPasswordForm";
        }

        redirectAttributes.addFlashAttribute("memberId", memberId);
        log.info("회원번호 {} 비밀번호 찾기", memberId);
        return "redirect:/login/password/" + memberId + "/change";
    }

    /**
     * 비밀번호 변경
     *
     * @Method GET
     */
    @GetMapping("/password/{memberId}/change")
    public String changePassword(@ModelAttribute("passwordChangeForm") PasswordChangeForm form, @PathVariable Long memberId) {
        log.debug("memberId {}", memberId);
        return "common/changePasswordForm";
    }

    @PostMapping("/password/{memberId}/change")
    public String changePasswordPost(
            @Validated @ModelAttribute PasswordChangeForm form,
            BindingResult bindingResult,
            @PathVariable Long memberId) {

        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            return "common/changePasswordForm";
        }

        if (!form.getPassword().equals(form.getCheckPassword())) {
            bindingResult.reject("notEqualPassword", "비밀번호가 일치하지 않습니다.");
            log.debug("비밀번호 확인이 일치하지 않음");
            return "common/changePasswordForm";
        }

        try {
            log.debug("memberId {}", memberId);
            log.info("회원번호 {} 비밀번호 변경", memberId);
            memberService.changePassword(memberId, form.getPassword());
            return "redirect:/login";
        } catch (NoSuchException e) {
            log.warn("비정상적 접근 발생");
            return "redirect:/";
        }
    }

    private static String blindEmail(String email) {
        String secret = "";
        int count = 0;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '.') {
                secret += email.substring(i);
                break;
            }
            if (email.charAt(i) == '@') {
                secret += "@";
                count = 0;
                continue;
            }
            if (count < 2) {
                secret += email.charAt(i);
                count++;
                continue;
            }
            secret += "*";
        }

        return secret;
    }

    private static void expiredSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
