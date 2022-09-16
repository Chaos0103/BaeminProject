package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.delivery.controller.form.NicknameUpdateForm;
import project.delivery.controller.form.PasswordUpdateForm;
import project.delivery.domain.member.Member;
import project.delivery.dto.BasketDto;
import project.delivery.dto.NotificationDto;
import project.delivery.exception.NoSuchException;
import project.delivery.login.Login;
import project.delivery.service.MemberService;
import project.delivery.service.query.NotificationQueryService;
import project.delivery.service.query.BasketQueryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final NotificationQueryService notificationQueryService;
    private final BasketQueryService basketQueryService;

    @GetMapping
    public String memberInfo(
            @ModelAttribute("nicknameUpdateForm") NicknameUpdateForm changeNicknameForm,
            @ModelAttribute("passwordUpdateForm") PasswordUpdateForm changePasswordForm,
            @Login Member loginMember, Model model) {
        headerInfo(loginMember, model);
        changeNicknameForm.setNewNickname(loginMember.getNickname());
        return "member/updateMemberForm";
    }

    @PostMapping("/nickname")
    public String changeNickname(
            @Validated @ModelAttribute NicknameUpdateForm changeNicknameForm,
            BindingResult bindingResult,
            @Login Member loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            return "member/updateMemberForm";
        }

        String nickname = changeNicknameForm.getNewNickname();

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
            @Validated @ModelAttribute PasswordUpdateForm changePasswordForm,
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
            expiredSession(request);

        } catch (NoSuchException e) {
            log.error("잘못된 접근이 발생하였습니다: {}", e.getMessage());
        }
        return "redirect:/";
    }

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember){
        return loginMember;
    }

    @ModelAttribute("nicknameUpdateForm")
    public NicknameUpdateForm changeNicknameForm() {
        return new NicknameUpdateForm();
    }

    @ModelAttribute("passwordUpdateForm")
    public PasswordUpdateForm changePasswordForm() {
        return new PasswordUpdateForm();
    }

    private void headerInfo(Member loginMember, Model model) {
        //알림 조회
        List<NotificationDto> notifications = notificationQueryService.findNotifications(loginMember.getId());
        //장바구니 조회
        BasketDto basket = basketQueryService.findBasket(loginMember.getId());

        model.addAttribute("notifications", notifications);
        model.addAttribute("basket", basket);
    }

    private static void expiredSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }
    }
}
