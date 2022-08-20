package project.delivery.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.delivery.controller.form.ChangePayPasswordForm;
import project.delivery.domain.Member;
import project.delivery.domain.TransactionType;
import project.delivery.dto.PayDto;
import project.delivery.dto.PayHistoryDto;
import project.delivery.service.PayService;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/pay")
public class PayController {

    private final PayService payService;

    @GetMapping
    public String payHome(
            @ModelAttribute("money") Money money,
            @ModelAttribute("passwordForm") ChangePayPasswordForm changePayPasswordForm,
            HttpSession session, Model model) {
        Member loginMember = getLoginMember(session);
        PayDto payDto = payService.findPay(loginMember.getId());
        model.addAttribute("payDto", payDto);
        model.addAttribute("payHistoryDtos", payDto.getPayHistoryDtos());
        return "member/pay";
    }

    @PostMapping("/charge")
    public String chargePayMoney(@ModelAttribute("money") Money money, HttpSession session) {
        Member loginMember = getLoginMember(session);

        payService.createPayHistory(loginMember.getId(), money.money, "배민페이", TransactionType.CHARGE);

        return "redirect:/pay";
    }

    @PostMapping("/refund")
    public String refundPayMoney(HttpSession session) {
        Member loginMember = getLoginMember(session);

        payService.refundPayMoney(loginMember.getId());

        return "redirect:/pay";
    }

    private Member getLoginMember(HttpSession session) {
        return (Member) session.getAttribute("loginMember");
    }

    @Data
    static class Money {
        private Integer money;
    }
}
