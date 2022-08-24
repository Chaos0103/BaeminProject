package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.domain.Category;
import project.delivery.domain.Member;
import project.delivery.domain.Notification;
import project.delivery.domain.Store;
import project.delivery.login.Login;
import project.delivery.service.NotificationService;
import project.delivery.service.StoreService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;
    private final NotificationService notificationService;

    @ModelAttribute("notifications")
    public List<Notification> notifications(@Login Member loginMember) {
        return notificationService.findByMemberId(loginMember.getId());
    }

    @GetMapping
    public String stores(@RequestParam Category category, Model model) {
        List<Store> stores = storeService.searchStores(category);
        model.addAttribute("stores", stores);
        return "/stores/stores";
    }

    @GetMapping("/{storeId}/detail")
    public String store(@PathVariable Long storeId, Model model) {
        Store store = storeService.detailStore(storeId);
        model.addAttribute("store", store);
        return "/stores/detail";
    }
}
