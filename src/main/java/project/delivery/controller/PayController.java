package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.delivery.controller.form.*;
import project.delivery.domain.*;
import project.delivery.login.Login;
import project.delivery.service.NotificationService;
import project.delivery.service.PayService;

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

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    @ModelAttribute("payMoneyUpdateForm")
    public PayMoneyUpdateForm payMoneyUpdateForm() {
        return new PayMoneyUpdateForm();
    }

    @ModelAttribute("changePayPasswordForm")
    public ChangePayPasswordForm changePayPasswordForm() {
        return new ChangePayPasswordForm();
    }

    @ModelAttribute("payMoneyRefundForm")
    public PayMoneyRefundForm payMoneyRefundForm() {
        return new PayMoneyRefundForm();
    }

    @ModelAttribute("nicknameUpdateForm")
    public NicknameUpdateForm nicknameUpdateForm() {
        return new NicknameUpdateForm();
    }

    @ModelAttribute("payCardSaveForm")
    public PayCardSaveForm payCardSaveForm() {
        return new PayCardSaveForm();
    }

    @ModelAttribute("payAccountSaveForm")
    public PayAccountSaveForm payAccountSaveForm() {
        return new PayAccountSaveForm();
    }

    @ModelAttribute("banks")
    public Bank[] banks() {
        return Bank.values();
    }

    @ModelAttribute("cards")
    public Card[] cards() {
        return Card.values();
    }

    @GetMapping
    public String payHome(@Login Member loginMember, Model model) {
        inputModel(loginMember, model);
        model.addAttribute("chargeModal", false);
        model.addAttribute("changePayPasswordModal", false);
        model.addAttribute("addPayAccount", false);
        model.addAttribute("addPayCard", false);
        return "member/pay";
    }

    @PostMapping("/charge")
    public String chargePayMoney(
            @Validated @ModelAttribute PayMoneyUpdateForm form,
            BindingResult bindingResult,
            @Login Member loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("필드 에러 발생: {}개", bindingResult.getErrorCount());
            inputModel(loginMember, model);
            model.addAttribute("chargeModal", true);
            return "member/pay";
        }

        String content = "배민페이-" + form.getBank().getDescription();
        payService.createPayHistory(loginMember.getId(), form.getMoney(), content, TransactionType.CHARGE);
        return "redirect:/pay";
    }

    @PostMapping("/refund")
    public String refundPayMoney(@ModelAttribute PayMoneyRefundForm form, @Login Member loginMember) {
        String content = "배민페이-" + form.getBank().getDescription();
        payService.refundPayMoney(loginMember.getId(), content);
        return "redirect:/pay";
    }

    @PostMapping("/changePassword")
    public String editPayPassword(
            @Validated @ModelAttribute ChangePayPasswordForm form,
            BindingResult bindingResult,
            @Login Member loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("필드 에러 발생: {}개", bindingResult.getErrorCount());
            inputModel(loginMember, model);
            model.addAttribute("changePayPasswordModal", true);
            return "member/pay";
        }

        if (!loginMember.getPay().getPassword().equals(form.getNowPassword())) {
            bindingResult.reject("notEqualPassword");
            inputModel(loginMember, model);
            model.addAttribute("changePayPasswordModal", true);
            return "member/pay";
        }

        if (!form.getNewPassword().equals(form.getCheckNewPassword())) {
            bindingResult.reject("notEqualCheckPassword");
            inputModel(loginMember, model);
            model.addAttribute("changePayPasswordModal", true);
            return "member/pay";
        }

        payService.changePayPassword(loginMember.getId(), form.getNewPassword());
        log.debug("회원번호 {} 배민페이 비밀번호 변경: {} -> {}", loginMember.getId(), form.getNowPassword(), form.getNewPassword());
        log.info("회원번호 {} 배민페이 비밀번호 변경", loginMember.getId());
        return "redirect:/pay";
    }

    @PostMapping("/account/{accountId}/nickname")
    public String editAccountNickname(
            @PathVariable Long accountId,
            @Validated @ModelAttribute NicknameUpdateForm form,
            BindingResult bindingResult,
            @Login Member loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("필드 에러 발생: {}개", bindingResult.getErrorCount());
            inputModel(loginMember, model);
            return "member/pay";
        }

        payService.updatePayAccountNickname(accountId, form.getNewNickname());
        return "redirect:/pay";
    }

    @GetMapping("/account/{accountId}/delete")
    public String deletePayAccount(@PathVariable Long accountId) {
        payService.deletePayAccount(accountId);
        return "redirect:/pay";
    }

    @PostMapping("/account/add")
    public String addPayAccount(
            @Validated @ModelAttribute PayAccountSaveForm form,
            BindingResult bindingResult,
            @Login Member loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("필드 에러 발생: {}개", bindingResult.getErrorCount());
            inputModel(loginMember, model);
            model.addAttribute("addPayAccount", true);
            return "member/pay";
        }

        PayAccount payAccount = new PayAccount(null, form.getBank(), form.getAccountNumber());
        Long payAccountId = payService.createPayAccount(loginMember.getId(), payAccount);
        log.debug("회원번호 {} 계좌 등록 {}", loginMember.getId(), payAccountId);
        log.info("회원번호 {} 계좌 등록", loginMember.getId());
        return "redirect:/pay";
    }

    @PostMapping("/card/{cardId}/nickname")
    public String editCardNickname(
            @PathVariable Long cardId,
            @Validated @ModelAttribute NicknameUpdateForm form,
            BindingResult bindingResult,
            @Login Member loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("필드 에러 발생: {}개", bindingResult.getErrorCount());
            inputModel(loginMember, model);
            return "member/pay";
        }

        payService.updatePayCardNickname(cardId, form.getNewNickname());
        return "redirect:/pay";
    }

    @GetMapping("/card/{cardId}/delete")
    public String deletePayCard(@PathVariable Long cardId) {
        payService.deletePayCard(cardId);
        return "redirect:/pay";
    }

    @PostMapping("/card/add")
    public String addPayCard(
            @Validated @ModelAttribute PayCardSaveForm form,
            BindingResult bindingResult,
            @Login Member loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("필드 에러 발생: {}개", bindingResult.getErrorCount());
            inputModel(loginMember, model);
            model.addAttribute("addPayCard", true);
            return "member/pay";
        }

        PayCard payCard = new PayCard(form.getCard(), form.getCardNumber(), form.getExpirationDate(), form.getCvc(), form.getPassword());
        Long payCardId = payService.createPayCard(loginMember.getId(), payCard);
        log.debug("회원번호 {} 카드 등록 {}", loginMember.getId(), payCardId);
        log.info("회원번호 {} 카드 등록", loginMember.getId());
        return "redirect:/pay";
    }

    private void inputModel(Member loginMember, Model model) {
        Pay pay = payService.findPay(loginMember.getId());
        List<PayAccount> payAccounts = payService.findPayAccount(pay.getId());
        List<PayCard> payCards = payService.findPayCard(pay.getId());
        model.addAttribute("pay", pay);
        model.addAttribute("payHistories", pay.getPayHistories());
        model.addAttribute("payAccounts", payAccounts);
        model.addAttribute("payCards", payCards);
    }
}
