package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.delivery.controller.form.DeliveryAddForm;
import project.delivery.controller.form.OrderAddForm;
import project.delivery.domain.Address;
import project.delivery.domain.Member;
import project.delivery.domain.Notification;
import project.delivery.domain.order.Delivery;
import project.delivery.domain.order.ReceiptType;
import project.delivery.dto.OrderDto;
import project.delivery.login.Login;
import project.delivery.service.NotificationService;
import project.delivery.service.OrderService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final NotificationService notificationService;
    private final OrderService orderService;

    @ModelAttribute("notifications")
    public List<Notification> notifications(@Login Member loginMember) {
        return notificationService.findByMemberId(loginMember.getId());
    }

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    @GetMapping("/delivery")
    public String deliveryOrder(
            @ModelAttribute("deliveryAddForm") DeliveryAddForm deliveryAddForm,
            @ModelAttribute("orderAddForm") OrderAddForm orderAddForm,
            @Login Member loginMember) {
        deliveryAddForm.setZipcode(loginMember.getAddress().getZipcode());
        deliveryAddForm.setMainAddress(loginMember.getAddress().getMainAddress());
        deliveryAddForm.setPhone(loginMember.getPhone());
        return "stores/deliveryOrder";
    }

    @PostMapping("/delivery")
    public String delivery(DeliveryAddForm deliveryAddForm, OrderAddForm orderAddForm, @Login Member loginMember) {

        String safeNumber = deliveryAddForm.getPhone();
        if (deliveryAddForm.getSafeNumber()) {
            safeNumber = "010-9999-9999";
        }
        Address address = new Address(loginMember.getAddress().getZipcode(), deliveryAddForm.getMainAddress(), deliveryAddForm.getDetailAddress());
        Delivery delivery = new Delivery(address, deliveryAddForm.getPhone(), safeNumber, deliveryAddForm.getRequirement());
        OrderDto orderDto = new OrderDto(delivery, ReceiptType.DELIVERY, orderAddForm.getDisposable(), orderAddForm.getSideDish(), orderAddForm.getRequirement());
        Long orderId = orderService.order(loginMember.getId(), orderDto);

        log.debug("orderId = {}", orderId);
        return "redirect:/";
    }

    @GetMapping("/packing")
    public String packingOrder(
            @ModelAttribute("deliveryAddForm") DeliveryAddForm deliveryAddForm,
            @ModelAttribute("orderAddForm") OrderAddForm orderAddForm,
            @Login Member loginMember) {
        deliveryAddForm.setPhone(loginMember.getPhone());
        return "stores/packingOrder";
    }

    @PostMapping("/packing")
    public String packing(DeliveryAddForm deliveryAddForm, OrderAddForm orderAddForm, @Login Member loginMember) {

        String safeNumber = deliveryAddForm.getPhone();
        if (deliveryAddForm.getSafeNumber()) {
            safeNumber = "010-9999-9999";
        }
//        orderService.order(loginMember.getId(), );
        return "redirect:/";
    }
}
