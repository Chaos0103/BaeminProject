package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.controller.form.DeliveryAddForm;
import project.delivery.controller.form.OrderAddForm;
import project.delivery.controller.form.PaymentAddForm;
import project.delivery.domain.Address;
import project.delivery.domain.coupon.Coupon;
import project.delivery.domain.basket.Basket;
import project.delivery.domain.basket.BasketMenu;
import project.delivery.domain.order.Delivery;
import project.delivery.domain.order.PaymentMethod;
import project.delivery.domain.order.ReceiptType;
import project.delivery.dto.*;
import project.delivery.login.Login;
import project.delivery.service.*;
import project.delivery.service.query.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final GlobalInformation globalInformation;

    private final OrderService orderService;
    private final StoreQueryService storeQueryService;

    private final BasketQueryService basketQueryService;
    private final CouponQueryService couponQueryService;
    private final PointQueryService pointQueryService;

    /**
     * @URL: localhost:8080/orders/{basketId}/delivery
     */
    @GetMapping("/{basketId}/delivery")
    public String deliveryOrder(
            @ModelAttribute("deliveryAddForm") DeliveryAddForm deliveryAddForm,
            @ModelAttribute("orderAddForm") OrderAddForm orderAddForm,
            @ModelAttribute("paymentAddForm") PaymentAddForm paymentAddForm,
            @Login LoginMember loginMember, @PathVariable Long basketId, Model model) {

        globalInformation.headerInfo(loginMember, model);

        Basket basket = basketQueryService.findBasketById(basketId);

        Integer totalAmount = getTotalAmount(basket);
        Integer deliveryTip = storeQueryService.findDeliveryTip(basket.getStore().getId(), totalAmount);

        paymentAddForm.setOrderAmount(totalAmount);
        paymentAddForm.setDeliveryTip(deliveryTip);
        deliveryAddForm.setMainAddress(loginMember.getAddress().getMainAddress());
        deliveryAddForm.setPhone(loginMember.getPhone());

        Integer totalPoint = pointQueryService.findTotalPoint(loginMember.getId());
        List<Coupon> coupons = couponQueryService.findAvailableCoupons(loginMember.getId());
        model.addAttribute("totalPoint", totalPoint);
        model.addAttribute("coupons", coupons);
        return "stores/deliveryOrder";
    }

    @PostMapping("/{basketId}/delivery")
    public String delivery(DeliveryAddForm deliveryAddForm, OrderAddForm orderAddForm, PaymentAddForm paymentAddForm, @Login LoginMember loginMember) {
        log.debug("paymentAddForm = {}", paymentAddForm);
        String safeNumber = deliveryAddForm.getPhone();
        if (deliveryAddForm.getSafeNumber()) {
            safeNumber = "010-9999-9999";
        }

        PaymentDto paymentDto = new PaymentDto(paymentAddForm.getPaymentMethod(), paymentAddForm.getOrderAmount(), paymentAddForm.getCouponId(), paymentAddForm.getPoint(), paymentAddForm.getDeliveryTip(), paymentAddForm.getTotalAmount());
        Address address = new Address(loginMember.getAddress().getZipcode(), deliveryAddForm.getMainAddress(), deliveryAddForm.getDetailAddress());
        Delivery delivery = new Delivery(address, deliveryAddForm.getPhone(), safeNumber, deliveryAddForm.getRequirement());
        OrderDto orderDto = new OrderDto(delivery, ReceiptType.DELIVERY, orderAddForm.getDisposable(), orderAddForm.getSideDish(), orderAddForm.getRequirement());
        Long orderId = orderService.order(loginMember.getId(), orderDto, paymentDto);

        log.debug("orderId = {}", orderId);
        return "redirect:/";
    }

    @GetMapping("/{basketId}/packing")
    public String packingOrder(
            @ModelAttribute("deliveryAddForm") DeliveryAddForm deliveryAddForm,
            @ModelAttribute("orderAddForm") OrderAddForm orderAddForm,
            @ModelAttribute("paymentAddForm") PaymentAddForm paymentAddForm,
            @Login LoginMember loginMember, Model model, @PathVariable Long basketId) {
        globalInformation.headerInfo(loginMember, model);
        deliveryAddForm.setPhone(loginMember.getPhone());
        Basket basket = basketQueryService.findBasketById(basketId);
        deliveryAddForm.setZipcode(basket.getStore().getBusinessAddress().getZipcode());
        deliveryAddForm.setMainAddress(basket.getStore().getBusinessAddress().getMainAddress());
        Integer totalAmount = getTotalAmount(basket);
        deliveryAddForm.setDetailAddress(basket.getStore().getBusinessAddress().getDetailAddress());
        paymentAddForm.setOrderAmount(totalAmount);
        Integer totalPoint = pointQueryService.findTotalPoint(loginMember.getId());
        List<Coupon> coupons = couponQueryService.findAvailableCoupons(loginMember.getId());
        model.addAttribute("store", basket.getStore());
        model.addAttribute("totalPoint", totalPoint);
        model.addAttribute("coupons", coupons);
        return "stores/packingOrder";
    }

    @PostMapping("/{basketId}/packing")
    public String packing(DeliveryAddForm deliveryAddForm, OrderAddForm orderAddForm, PaymentAddForm paymentAddForm, @Login LoginMember loginMember, @PathVariable Long basketId) {
        String safeNumber = deliveryAddForm.getPhone();
        if (deliveryAddForm.getSafeNumber()) {
            safeNumber = "010-9999-9999";
        }

        PaymentDto paymentDto = new PaymentDto(paymentAddForm.getPaymentMethod(), paymentAddForm.getOrderAmount(), paymentAddForm.getCouponId(), paymentAddForm.getPoint(), 0, paymentAddForm.getTotalAmount());
        Address address = new Address(deliveryAddForm.getZipcode(), deliveryAddForm.getMainAddress(), deliveryAddForm.getDetailAddress());
        Delivery delivery = new Delivery(address, deliveryAddForm.getPhone(), safeNumber, "");
        OrderDto orderDto = new OrderDto(delivery, ReceiptType.PACKING, orderAddForm.getDisposable(), orderAddForm.getSideDish(), orderAddForm.getRequirement());
        Long orderId = orderService.order(loginMember.getId(), orderDto, paymentDto);

        log.debug("orderId = {}", orderId);
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/coupons")
    public Map<String, Object> usingCoupon(@RequestParam("couponId") Long couponId) {
        Coupon coupon = couponQueryService.findById(couponId);
        Map<String, Object> couponData = new HashMap<>();
        couponData.put("couponId", coupon.getId());
        couponData.put("discountPrice", coupon.getDiscountPrice());
        return couponData;
    }

    @ModelAttribute("paymentMethods")
    public PaymentMethod[] paymentMethods() {
        return PaymentMethod.values();
    }

    private Integer getTotalAmount(Basket basket) {
        List<Integer> orderPrices = basket.getBasketMenus().stream()
                .map(BasketMenu::getOrderPrice)
                .toList();
        return orderPrices.stream()
                .mapToInt(orderPrice -> orderPrice).sum();
    }
}
