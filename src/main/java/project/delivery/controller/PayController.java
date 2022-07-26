package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.delivery.controller.form.*;
import project.delivery.domain.pay.*;
import project.delivery.dto.*;
import project.delivery.login.Login;
import project.delivery.service.*;
import project.delivery.service.query.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/pays")
public class PayController {

    private final GlobalInformation globalInformation;

    private final PayService payService;
    private final PayQueryService payQueryService;

    /**
     * @URL: localhost:8080/members/pays
     */
    @GetMapping
    public String payHome(@Login LoginMember loginMember, Model model) {
        globalInformation.headerInfo(loginMember, model);
        globalInformation.topInfo(loginMember, model);

        modalInit(model);
        inputModel(loginMember, model);
        return "member/pay";
    }

    /**
     * @URL: localhost:8080/members/pays/charge
     */
    @PostMapping("/charge")
    public String chargePayMoney(
            @Validated @ModelAttribute PayMoneyUpdateForm form,
            BindingResult bindingResult,
            @Login LoginMember loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("필드 에러 발생: {}개", bindingResult.getErrorCount());
            inputModel(loginMember, model);
            model.addAttribute("chargeModal", true);
            return "member/pay";
        }

        String content = "배민페이-" + form.getBank().getDescription();
        Long payHistoryId = payService.chargePayMoney(loginMember.getId(), form.getMoney(), content);
        log.info("회원번호 {}, 사용내역 {}", loginMember.getId(), payHistoryId);
        return "redirect:/members/pays";
    }

    /**
     * @URL: localhost:8080/members/pays/refund
     */
    @PostMapping("/refund")
    public String refundPayMoney(@ModelAttribute PayMoneyRefundForm form, @Login LoginMember loginMember) {
        String content = "배민페이-" + form.getBank().getDescription();
        Long payHistoryId = payService.refundPayMoney(loginMember.getId(), content);
        log.info("회원번호 {}, 사용내역 {}", loginMember.getId(), payHistoryId);
        return "redirect:/members/pays";
    }

    /**
     * @URL: localhost:8080/members/pays/changePassword
     */
    @PostMapping("/changePassword")
    public String editPayPassword(
            @Validated @ModelAttribute ChangePayPasswordForm form,
            BindingResult bindingResult,
            @Login LoginMember loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("필드 에러 발생: {}개", bindingResult.getErrorCount());
            inputModel(loginMember, model);
            model.addAttribute("changePayPasswordModal", true);
            return "member/pay";
        }

        String password = payQueryService.findPayPassword(loginMember.getId());
        if (!password.equals(form.getNowPassword())) {
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
        return "redirect:/members/pays";
    }

    /**
     * @URL: localhost:8080/members/pays/account/{accountId}/nickname
     */
    @PostMapping("/account/{accountId}/nickname")
    public String editAccountNickname(
            @PathVariable Long accountId,
            @Validated @ModelAttribute NicknameUpdateForm form,
            BindingResult bindingResult,
            @Login LoginMember loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("필드 에러 발생: {}개", bindingResult.getErrorCount());
            inputModel(loginMember, model);
            return "member/pay";
        }

        payService.updatePayAccountNickname(accountId, form.getNewNickname());
        return "redirect:/members/pays";
    }

    /**
     * @URL: localhost:8080/members/pays/account/{accountId}/delete
     */
    @GetMapping("/account/{accountId}/delete")
    public String deletePayAccount(@PathVariable Long accountId) {
        payService.deletePayAccount(accountId);
        return "redirect:/members/pays";
    }

    /**
     * @URL: localhost:8080/members/pays/account/add
     */
    @PostMapping("/account/add")
    public String addPayAccount(
            @Validated @ModelAttribute PayAccountSaveForm form,
            BindingResult bindingResult,
            @Login LoginMember loginMember, Model model) {

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
        return "redirect:/members/pays";
    }

    @PostMapping("/card/{cardId}/nickname")
    public String editCardNickname(
            @PathVariable Long cardId,
            @Validated @ModelAttribute NicknameUpdateForm form,
            BindingResult bindingResult,
            @Login LoginMember loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("필드 에러 발생: {}개", bindingResult.getErrorCount());
            inputModel(loginMember, model);
            return "member/pay";
        }

        payService.updatePayCardNickname(cardId, form.getNewNickname());
        return "redirect:/members/pays";
    }

    @GetMapping("/card/{cardId}/delete")
    public String deletePayCard(@PathVariable Long cardId) {
        payService.deletePayCard(cardId);
        return "redirect:/members/pays";
    }

    @PostMapping("/card/add")
    public String addPayCard(
            @Validated @ModelAttribute PayCardSaveForm form,
            BindingResult bindingResult,
            @Login LoginMember loginMember, Model model) {

        if (bindingResult.hasErrors()) {
            log.debug("필드 에러 발생: {}개", bindingResult.getErrorCount());
            inputModel(loginMember, model);
            model.addAttribute("addPayCard", true);
            return "member/pay";
        }

        PayCard payCard = new PayCard(null, form.getCard(), form.getCardNumber(), form.getExpirationDate(), form.getCvc(), form.getPassword());
        Long payCardId = payService.createPayCard(loginMember.getId(), payCard);
        log.debug("회원번호 {} 카드 등록 {}", loginMember.getId(), payCardId);
        log.info("회원번호 {} 카드 등록", loginMember.getId());
        return "redirect:/members/pays";
    }

    private void inputModel(LoginMember loginMember, Model model) {
        PayDto pay = payQueryService.findPay(loginMember.getId());
        List<PayAccountDto> payAccounts = payQueryService.findPayAccount(pay.getId());
        List<PayCardDto> payCards = payQueryService.findPayCard(pay.getId());
        model.addAttribute("pay", pay);
        model.addAttribute("payHistories", pay.getPayHistories());
        model.addAttribute("payAccounts", payAccounts);
        model.addAttribute("payCards", payCards);
    }

    private void modalInit(Model model) {
        model.addAttribute("chargeModal", false);
        model.addAttribute("changePayPasswordModal", false);
        model.addAttribute("addPayAccount", false);
        model.addAttribute("addPayCard", false);
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

}
