package project.delivery.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.delivery.domain.Member;
import project.delivery.exception.NoSuchException;
import project.delivery.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String memberInfo(
            @ModelAttribute("changeNicknameForm") ChangeNicknameForm changeNicknameForm,
            @ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
            HttpSession session, Model model) {

        Member loginMember = getLoginMember(session);

        changeNicknameForm.setNewNickname(loginMember.getNickname());
        model.addAttribute("loginMember", loginMember);

        return "member/updateMemberForm";
    }

    @PostMapping("/nickname")
    public String changeNickname(
            @Validated @ModelAttribute("changeNicknameForm") ChangeNicknameForm changeNicknameForm,
            BindingResult bindingResult,
            HttpSession session, Model model) {

        Member loginMember = getLoginMember(session);
        String nickname = changeNicknameForm.getNewNickname();

        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            model.addAttribute("loginMember", loginMember);
            model.addAttribute("changePasswordForm", new ChangePasswordForm());
            return "member/updateMemberForm";
        }

        if (loginMember.getNickname().equals(nickname)) {
            log.debug("기존 닉네임과 동일합니다");
            model.addAttribute("loginMember", loginMember);
            model.addAttribute("changePasswordForm", new ChangePasswordForm());
            model.addAttribute("equalNickname", "기존 닉네임과 동일합니다");
            return "member/updateMemberForm";
        }

        try {
            memberService.changeNickname(loginMember.getId(), nickname);
            log.debug("회원번호 {} 닉네임 변경: {} -> {}", loginMember.getId(), loginMember.getNickname(), nickname);
            log.info("회원번호 {} 닉네임 변경", loginMember.getId());
            loginMember.changeNickname(nickname);
        } catch (NoSuchException e) {
            log.error("잘못된 접근이 발생하였습니다: {}", e.getMessage());
            return "redirect:/";
        }

        return "redirect:/members";
    }

    @PostMapping("/password")
    public String changePassword(
            @Validated @ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
            BindingResult bindingResult,
            HttpSession session, Model model) {

        Member loginMember = getLoginMember(session);
        String nowPassword = changePasswordForm.getNowPassword();
        String newPassword = changePasswordForm.getNewPassword();

        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            model.addAttribute("loginMember", loginMember);
            model.addAttribute("changeNicknameForm", new ChangeNicknameForm());
            return "member/updateMemberForm";
        }

        if (!loginMember.getPassword().equals(nowPassword)) {
            log.debug("비밀번호가 일치하지 않습니다");
            model.addAttribute("notEqualPassword", "비밀번호가 일치하지 않습니다");
            model.addAttribute("loginMember", loginMember);
            model.addAttribute("changeNicknameForm", new ChangeNicknameForm());
            return "member/updateMemberForm";
        }

        if (loginMember.getPassword().equals(newPassword)) {
            log.debug("기존 비밀번호와 같은 비밀번호를 설정할 수 없습니다");
            model.addAttribute("equalNowPassword", "기존 비밀번호와 같은 비밀번호를 설정할 수 없습니다");
            model.addAttribute("loginMember", loginMember);
            model.addAttribute("changeNicknameForm", new ChangeNicknameForm());
            return "member/updateMemberForm";
        }

        try {
            memberService.changePassword(loginMember.getId(), newPassword);
            log.debug("회원번호 {} 비밀번호 변경: {} -> {}", loginMember.getId(), nowPassword, newPassword);
            log.info("회원번호 {} 비밀번호 변경", loginMember.getId());
        } catch (NoSuchException e) {
            log.error("잘못된 접근이 발생하였습니다: {}", e.getMessage());
            return "redirect:/";
        }

        return "redirect:/members";
    }

    @GetMapping("/delete")
    public String deleteMember(HttpSession session, HttpServletRequest request) {
        Member loginMember = getLoginMember(session);

        try {
            memberService.deleteMember(loginMember.getId());
            log.info("회원번호 {} 탈퇴", loginMember.getId());

            HttpSession httpSession = request.getSession(false);
            if (httpSession != null) {
                httpSession.invalidate();
            }

        } catch (NoSuchException e) {
            log.error("잘못된 접근이 발생하였습니다: {}", e.getMessage());
        }
        return "redirect:/";
    }

    private Member getLoginMember(HttpSession session) {
        return (Member) session.getAttribute("loginMember");
    }

    @Data
    static class ChangeNicknameForm {
        @NotBlank(message = "닉네임을 입력해주세요")
        @Length(min = 2, max = 10, message = "닉네임을 2~10자로 입력해주세요")
        private String newNickname;
    }

    @Data
    static class ChangePasswordForm {
        @NotBlank(message = "현재 비밀번호를 입력해주세요")
        @Length(min = 10, max = 20, message = "비밀번호를 10~20자로 입력해주세요")
        private String nowPassword;
        @NotBlank(message = "신규 비밀번호를 입력해주세요")
        @Length(min = 10, max = 20, message = "비밀번호를 10~20자로 입력해주세요")
        private String newPassword;
    }
}
