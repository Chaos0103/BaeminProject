package project.delivery.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.controller.form.ChangePayPasswordForm;
import project.delivery.domain.Member;
import project.delivery.domain.Notification;
import project.delivery.domain.Pay;
import project.delivery.domain.TransactionType;
import project.delivery.login.Login;
import project.delivery.service.NotificationService;
import project.delivery.service.PayService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/pay")
public class PayController {

    private final PayService payService;
    private final NotificationService notificationService;

    @ModelAttribute("notifications")
    public List<Notification> notifications(@Login Member loginMember) {
        return notificationService.findByMemberId(loginMember.getId());
    }

    @GetMapping
    public String payHome(
            @ModelAttribute("money") Money money,
            @ModelAttribute("passwordForm") ChangePayPasswordForm changePayPasswordForm,
            HttpSession session, Model model) {
        Member loginMember = getLoginMember(session);
        Pay pay = payService.findPay(loginMember.getId());
        model.addAttribute("pay", pay);
        model.addAttribute("payHistories", pay.getPayHistories());
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
