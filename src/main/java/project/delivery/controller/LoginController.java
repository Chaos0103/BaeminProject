package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.delivery.controller.form.ChangePasswordForm;
import project.delivery.controller.form.EmailFindForm;
import project.delivery.controller.form.FindPasswordForm;
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

        try {
            Member loginMember = loginService.login(form.getEmail(), form.getPassword());

            HttpSession session = request.getSession();
            session.setAttribute("loginMember", loginMember);
            log.info("회원 번호 {} 로그인", loginMember.getId());
            return "redirect:" + redirectURL;
        } catch (NoSuchException e) {
            bindingResult.reject("loginFail", e.getMessage());
            log.debug("로그인 예외 발생: {}", e.getMessage());
            return "common/login";
        }
    }

    /**
     * 로그아웃
     *
     * @Method GET
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
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

        try {
            Member member = loginService.findLoginEmail(form.getPhone());

            redirectAttributes.addFlashAttribute("email", member.getEmail());
            redirectAttributes.addFlashAttribute("createdDate", member.getCreatedDate());
            log.info("회원번호 {} 아이디 찾기", member.getId());
            return "redirect:/login/loginId/success";
        } catch (NoSuchException e) {
            log.debug("이메일 찾기 예외 발생: {}", e.getMessage());
            return "common/findLoginIdForm";
        }

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
    public String findPassword(@ModelAttribute("findPasswordForm") FindPasswordForm form) {
        return "common/findPasswordForm";
    }

    /**
     * 비밀번호 찾기
     * @Method POST
     */
    @PostMapping("/password")
    public String findPasswordPost(
            @Validated @ModelAttribute FindPasswordForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            return "common/findPasswordForm";
        }

        try {
            Long memberId = loginService.findLoginPassword(form.getEmail(), form.getPhone());

            redirectAttributes.addFlashAttribute("memberId", memberId);
            log.info("회원번호 {} 비밀번호 찾기", memberId);
            return "redirect:/login/password/" + memberId + "/change";
        } catch (NoSuchException e) {
            log.debug("비밀번호 찾기 예외 발생");
            return "common/findPasswordForm";
        }

    }

    /**
     * 비밀번호 변경
     *
     * @Method GET
     */
    @GetMapping("/password/{memberId}/change")
    public String changePassword(@ModelAttribute("changePasswordForm") ChangePasswordForm form, @PathVariable Long memberId) {
        log.debug("memberId {}", memberId);
        return "common/changePasswordForm";
    }

    @PostMapping("/password/{memberId}/change")
    public String changePasswordPost(
            @Validated @ModelAttribute ChangePasswordForm form,
            BindingResult bindingResult,
            @PathVariable Long memberId) {
        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            return "common/changePasswordForm";
        }
        if (!form.getPassword().equals(form.getCheckPassword())) {
            log.debug("비밀번호 확인이 일치하지 않음");
            bindingResult.reject("notEqualPassword", "비밀번호가 일치하지 않습니다");
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
}
