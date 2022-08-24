package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.delivery.controller.form.MemberSaveForm;
import project.delivery.domain.Address;
import project.delivery.domain.Member;
import project.delivery.exception.DuplicateException;
import project.delivery.service.MemberService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/sign-up")
public class SignUpController {

    private final MemberService memberService;

    @GetMapping
    public String signUpForm(@ModelAttribute("memberSaveForm") MemberSaveForm form) {
        return "common/joinMemberForm";
    }

    @PostMapping
    public String signUp(@Validated @ModelAttribute MemberSaveForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            return "common/joinMemberForm";
        }

        try {
            Member member = createMember(form);

            Long memberId = memberService.joinMember(member);
            log.info("신규 회원가입 발생: 회원 번호 {}", memberId);

            return "redirect:/";
        } catch (DuplicateException e) {
            bindingResult.reject("joinFail", e.getMessage());
            log.debug("회원가입시 중복 예외 발생: {}", e.getMessage());

            return "common/joinMemberForm";
        }
    }

    private static Member createMember(MemberSaveForm form) {
        Address address = new Address(form.getZipcode(), form.getMainAddress(), form.getDetailAddress());
        return new Member(form.getEmail(), form.getPassword(), form.getUsername(), form.getBirth(), form.getPhone(), form.getNickname(), address);
    }

}
