package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.domain.*;
import project.delivery.login.Login;
import project.delivery.service.BookmarkService;
import project.delivery.service.MenuService;
import project.delivery.service.NotificationService;
import project.delivery.service.StoreService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;
    private final MenuService menuService;
    private final BookmarkService bookmarkService;
    private final NotificationService notificationService;

    @ModelAttribute("notifications")
    public List<Notification> notifications(@Login Member loginMember) {
        return notificationService.findByMemberId(loginMember.getId());
    }

    @GetMapping
    public String stores(@RequestParam Category category, Model model) {
        List<Store> stores = storeService.findStoresByCategory(category);
        model.addAttribute("stores", stores);
        return "/stores/stores";
    }

    @GetMapping("/{storeId}/detail")
    public String store(@PathVariable Long storeId, Model model) {
        Store store = storeService.findStoreById(storeId);
        List<StoreImage> storeImages = storeService.findStoreBannerImages(storeId);
        List<MenuCategory> categories = storeService.findCategory(storeId);

        List<Menu> menus = menuService.findMenuByStoreId(storeId);

        List<Long> menuIds = getMenuIds(menus);
        List<MenuOption> menuOptions = menuService.findMenuOptionByMenuIds(menuIds);
        List<MenuSubOptionCategory> menuSubOptionCategorise = menuService.findMenuSubOptionCategory(menuIds);

        List<Long> categoryIds = getCategoryIds(menuSubOptionCategorise);
        List<MenuSubOption> menuSubOptions = menuService.findMenuSubOption(categoryIds);

        model.addAttribute("store", store);
        model.addAttribute("categories", categories);
        model.addAttribute("menuOptions", menuOptions);
        model.addAttribute("menus", menus);
        model.addAttribute("storeImages", storeImages);
        model.addAttribute("menuSubOptionCategorise", menuSubOptionCategorise);
        model.addAttribute("menuSubOptions", menuSubOptions);
        return "/stores/detail";
    }

    @PostMapping("/{storeId}/bookmark")
    public String addBookmark(@PathVariable Long storeId, @Login Member loginMember) {
        bookmarkService.addBookmark(loginMember, storeId);
        return "redirect:/stores/{storeId}/detail";
    }

    private static List<Long> getMenuIds(List<Menu> menus) {
        return menus.stream()
                .map(Menu::getId)
                .toList();
    }

    private static List<Long> getCategoryIds(List<MenuSubOptionCategory> menuSubOptionCategorise) {
        return menuSubOptionCategorise.stream()
                .map(MenuSubOptionCategory::getId)
                .toList();
    }
}
