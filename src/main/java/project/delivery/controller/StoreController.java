package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.domain.*;
import project.delivery.login.Login;
import project.delivery.service.*;

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
    private final ReviewService reviewService;

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

        List<Review> reviews = reviewService.findAllByStoreId(storeId);

        int[] ratingData = getRatingData(reviews);
        float[] ratingPercent = getRatingPercent(reviews, ratingData);

        model.addAttribute("store", store);
        model.addAttribute("categories", categories);
        model.addAttribute("menuOptions", menuOptions);
        model.addAttribute("menus", menus);
        model.addAttribute("storeImages", storeImages);
        model.addAttribute("menuSubOptionCategorise", menuSubOptionCategorise);
        model.addAttribute("menuSubOptions", menuSubOptions);

        model.addAttribute("reviews", reviews);
        model.addAttribute("ratingData", ratingData);
        model.addAttribute("ratingPercent", ratingPercent);
        return "/stores/detail";
    }

    @PostMapping("/{storeId}/bookmark")
    public String addBookmark(@PathVariable Long storeId, @Login Member loginMember) {
        bookmarkService.addBookmark(loginMember, storeId);
        return "redirect:/stores/{storeId}/detail";
    }

    private static float[] getRatingPercent(List<Review> reviews, int[] ratingData) {
        float[] ratingPercent = new float[6];
        if (reviews.size() == 0) {
            return ratingPercent;
        }
        for (int i = 1; i < 6; i++) {
            ratingPercent[i] = ((float) ratingData[i] / reviews.size()) * 100;
        }
        return ratingPercent;
    }

    private static int[] getRatingData(List<Review> reviews) {
        int[] rating = new int[6];
        for (Review review : reviews) {
            rating[review.getRating()]++;
        }
        return rating;
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
