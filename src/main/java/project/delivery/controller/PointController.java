package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.delivery.controller.form.VoucherSaveForm;
import project.delivery.domain.*;
import project.delivery.dto.PointHistorySearch;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.login.Login;
import project.delivery.service.NotificationService;
import project.delivery.service.PointService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {

    private final PointService pointService;
    private final NotificationService notificationService;

    @ModelAttribute("notifications")
    public List<Notification> notifications(@Login Member loginMember) {
        return notificationService.findByMemberId(loginMember.getId());
    }

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    @ModelAttribute("pointTypes")
    public PointType[] pointTypes() {
        return PointType.values();
    }

    @ModelAttribute("voucherSaveForm")
    public VoucherSaveForm voucherSaveForm() {
        return new VoucherSaveForm();
    }

    @GetMapping
    public String pointHome(
            @ModelAttribute("search") PointHistorySearch search,
            @Login Member loginMember, Model model) {
        Point point = pointService.getPointByMember(loginMember.getId());
        List<PointHistory> pointHistories = pointService.findPointHistory(point.getId(), search);
        model.addAttribute("point", point);
        model.addAttribute("pointHistories", pointHistories);
        model.addAttribute("voucherError", false);
        return "member/point";
    }

    @PostMapping("/addVoucher")
    public String addVoucher(
            @ModelAttribute("search") PointHistorySearch search,
            @Validated @ModelAttribute VoucherSaveForm form,
            BindingResult bindingResult,
            @Login Member loginMember, Model model) {

        try {
            Integer pointValue = pointService.addVoucher(loginMember.getId(), form.getVoucherCode());
            log.info("회원번호 {} 상품권 등록: {} 충전", loginMember.getId(), pointValue);
            return "redirect:/points";
        } catch (NoSuchException | DuplicateException e) {
            bindingResult.reject("voucherError", e.getMessage());
            model.addAttribute("voucherError", true);
            log.debug(e.getMessage());
            Point point = pointService.getPointByMember(loginMember.getId());
            List<PointHistory> pointHistories = pointService.findPointHistory(point.getId(), search);
            model.addAttribute("point", point);
            model.addAttribute("pointHistories", pointHistories);
            return "member/point";
        }
    }
}
