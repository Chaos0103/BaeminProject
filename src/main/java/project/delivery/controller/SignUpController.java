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
import project.delivery.controller.form.JoinMemberForm;
import project.delivery.dto.create.CreateAddressDto;
import project.delivery.dto.create.CreateMemberDto;
import project.delivery.exception.DuplicateException;
import project.delivery.service.MemberService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/sign-up")
public class SignUpController {

    private final MemberService memberService;

    @GetMapping
    public String signUpForm(@ModelAttribute("joinMemberForm") JoinMemberForm joinMemberForm) {
        return "common/joinMemberForm";
    }

    @PostMapping
    public String signUp(@Validated @ModelAttribute JoinMemberForm joinMemberForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.debug("폼 데이터 검증시 예외 발생: {}개", bindingResult.getErrorCount());
            return "common/joinMemberForm";
        }

        try {
            CreateMemberDto createMemberDto = getCreateMemberDto(joinMemberForm);

            Long memberId = memberService.joinMember(createMemberDto);
            log.info("신규 회원가입 발생: 회원 번호 {}", memberId);

            return "redirect:/";
        } catch (DuplicateException e) {
            bindingResult.reject("joinFail", e.getMessage());
            log.debug("회원가입시 중복 예외 발생: {}", e.getMessage());

            return "common/joinMemberForm";
        }
    }

    private CreateMemberDto getCreateMemberDto(JoinMemberForm joinMemberForm) {
        CreateAddressDto createAddressDto = new CreateAddressDto(joinMemberForm.getZipcode(),
                joinMemberForm.getMainAddress(), joinMemberForm.getDetailAddress());

        return new CreateMemberDto(joinMemberForm.getEmail(), joinMemberForm.getPassword(),
                joinMemberForm.getUsername(), joinMemberForm.getBirth(), joinMemberForm.getPhone(),
                joinMemberForm.getNickname(), createAddressDto);
    }
}
