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
import project.delivery.domain.point.PointType;
import project.delivery.dto.*;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.login.Login;
import project.delivery.service.*;
import project.delivery.service.query.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/points")
public class PointController {

    private final GlobalInformation globalInformation;

    private final PointService pointService;
    private final PointQueryService pointQueryService;
    
    /**
     * @URL: localhost:8080/members/points
     */
    @GetMapping
    public String pointHome(
            @ModelAttribute("search") PointHistorySearch search,
            @Login LoginMember loginMember, Model model) {
        globalInformation.headerInfo(loginMember, model);
        globalInformation.topInfo(loginMember, model);

        //포인트 데이터 조회
        PointDto point = pointQueryService.findPointByMemberId(loginMember.getId());
        //포인트 내역 조회
        List<PointHistoryDto> pointHistories = pointQueryService.findPointHistoryByPointId(point.getId(), search);

        model.addAttribute("point", point);
        model.addAttribute("pointHistories", pointHistories);

        model.addAttribute("voucherError", false);
        return "member/point";
    }

    /**
     * @URL: localhost:8080/points/addVoucher
     */
    @PostMapping("/addVoucher")
    public String addVoucher(
            @ModelAttribute("search") PointHistorySearch search,
            @Validated @ModelAttribute VoucherSaveForm form,
            BindingResult bindingResult,
            @Login LoginMember loginMember, Model model) {

        try {
            Integer pointValue = pointService.voucherRegistration(loginMember.getId(), form.getVoucherCode());
            log.info("회원번호 {} 상품권 등록: {} 충전", loginMember.getId(), pointValue);
            return "redirect:/points";
        } catch (NoSuchException | DuplicateException e) {
            bindingResult.reject("voucherError", e.getMessage());
            model.addAttribute("voucherError", true);
            log.debug(e.getMessage());
            PointDto point = pointQueryService.findPointByMemberId(loginMember.getId());
            List<PointHistoryDto> pointHistories = pointQueryService.findPointHistoryByPointId(point.getId(), search);
            model.addAttribute("point", point);
            model.addAttribute("pointHistories", pointHistories);
            return "member/point";
        }
    }

    @ModelAttribute("pointTypes")
    public PointType[] pointTypes() {
        return PointType.values();
    }

    @ModelAttribute("voucherSaveForm")
    public VoucherSaveForm voucherSaveForm() {
        return new VoucherSaveForm();
    }
}
