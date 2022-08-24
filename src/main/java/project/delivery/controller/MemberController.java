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
import project.delivery.domain.Notification;
import project.delivery.exception.NoSuchException;
import project.delivery.login.Login;
import project.delivery.service.MemberService;
import project.delivery.service.NotificationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final NotificationService notificationService;

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember){
        return loginMember;
    }

    @ModelAttribute("changeNicknameForm")
    public ChangeNicknameForm changeNicknameForm() {
        return new ChangeNicknameForm();
    }

    @ModelAttribute("changePasswordForm")
    public ChangePasswordForm changePasswordForm() {
        return new ChangePasswordForm();
    }

    @ModelAttribute("notifications")
    public List<Notification> notifications(@Login Member loginMember) {
        return notificationService.findByMemberId(loginMember.getId());
    }

    @GetMapping
    public String memberInfo(
            @ModelAttribute("changeNicknameForm") ChangeNicknameForm changeNicknameForm,
            @ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
            @Login Member loginMember) {

        changeNicknameForm.setNewNickname(loginMember.getNickname());
        return "member/updateMemberForm";
    }

    @PostMapping("/nickname")
    public String changeNickname(
            @Validated @ModelAttribute("changeNicknameForm") ChangeNicknameForm changeNicknameForm,
            BindingResult bindingResult,
            @Login Member loginMember, Model model) {

        String nickname = changeNicknameForm.getNewNickname();

        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            return "member/updateMemberForm";
        }

        if (loginMember.getNickname().equals(nickname)) {
            log.debug("기존 닉네임과 동일합니다");
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
            @Login Member loginMember, Model model) {

        String nowPassword = changePasswordForm.getNowPassword();
        String newPassword = changePasswordForm.getNewPassword();

        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            return "member/updateMemberForm";
        }

        if (!loginMember.getPassword().equals(nowPassword)) {
            log.debug("비밀번호가 일치하지 않습니다");
            model.addAttribute("notEqualPassword", "비밀번호가 일치하지 않습니다");
            return "member/updateMemberForm";
        }

        if (loginMember.getPassword().equals(newPassword)) {
            log.debug("기존 비밀번호와 같은 비밀번호를 설정할 수 없습니다");
            model.addAttribute("equalNowPassword", "기존 비밀번호와 같은 비밀번호를 설정할 수 없습니다");
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
    public String deleteMember(@Login Member loginMember, HttpServletRequest request) {
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
