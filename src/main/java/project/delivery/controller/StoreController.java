package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.controller.form.BasketAddForm;
import project.delivery.domain.*;
import project.delivery.domain.member.Member;
import project.delivery.dto.BasketDto;
import project.delivery.dto.BasketMenuDto;
import project.delivery.dto.NotificationDto;
import project.delivery.dto.ReviewSearch;
import project.delivery.login.Login;
import project.delivery.service.*;

import java.util.ArrayList;
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
    private final BasketService basketService;

    /**
     * @URL: localhost:8080/stores
     */
    @GetMapping
    public String stores(@RequestParam Category category, @Login Member loginMember, Model model) {
        headerInfo(model, loginMember);

        List<Store> stores = storeService.findStoresByCategory(category);
        model.addAttribute("stores", stores);

        log.debug("category={}", category);
        return "/stores/stores";
    }

    /**
     * @URL: localhost:8080:/stores/{storeId}/detail
     */
    @GetMapping("/{storeId}/detail")
    public String store(@PathVariable Long storeId, Model model, @Login Member loginMember) {
        headerInfo(model, loginMember);

        Store store = storeService.findStoreById(storeId);
        List<DeliveryTipByAmount> deliveryTipByAmounts = storeService.findDeliveryTipByAmountByDeliveryId(store.getDeliveryInfo().getId());
        List<DeliveryTipByArea> deliveryTipByAreas = storeService.findDeliveryTipByAreaByDeliveryId(store.getDeliveryInfo().getId());
        List<StoreImage> storeImages = storeService.findStoreBannerImages(storeId);
        //MenuCategory join fetch Menu
        List<MenuCategory> categories = storeService.findMenuCategoryByStoreId(storeId);
        //Menu PK 조회
        List<Long> menuIds = getMenuIds(categories);
        List<MenuOption> menuOptions = menuService.findMenuOptionByMenuIds(menuIds);
        List<MenuSubCategory> menuSubOptionCategorise = menuService.findMenuSubOptionCategory(menuIds);

        List<Review> reviews = reviewService.findAllByStoreId(new ReviewSearch(storeId, false, "recent"));

        int[] ratingData = getRatingData(reviews);
        float[] ratingPercent = getRatingPercent(reviews, ratingData);

        Integer minDeliveryTip = deliveryTipByAmounts.get(0).getDeliveryTip();
        Integer maxDeliveryTip = deliveryTipByAmounts.get(deliveryTipByAmounts.size() - 1).getDeliveryTip();

        model.addAttribute("store", store);
        model.addAttribute("deliveryTipByAmounts", deliveryTipByAmounts);
        model.addAttribute("deliveryTipByAreas", deliveryTipByAreas);
        model.addAttribute("storeImages", storeImages);
        model.addAttribute("categories", categories);


        model.addAttribute("minDeliveryTip", minDeliveryTip);
        model.addAttribute("maxDeliveryTip", maxDeliveryTip);
        model.addAttribute("menuOptions", menuOptions);
        model.addAttribute("menuSubOptionCategorise", menuSubOptionCategorise);

        model.addAttribute("reviews", reviews);
        model.addAttribute("ratingData", ratingData);
        model.addAttribute("ratingPercent", ratingPercent);

        return "/stores/detail";
    }

    @GetMapping("/review")
    public String searchReview(@ModelAttribute ReviewSearch search, Model model) {
        List<Review> reviews = reviewService.findAllByStoreId(search);
        model.addAttribute("reviews", reviews);
        return "/stores/detail :: #review";
    }


    @PostMapping("/{storeId}/bookmark")
    public String addBookmark(@PathVariable Long storeId, @Login Member loginMember) {
        bookmarkService.addBookmark(loginMember, storeId);
        return "redirect:/stores/{storeId}/detail";
    }

    @PostMapping("/{storeId}/basket")
    public String addBasket(@PathVariable Long storeId, BasketAddForm form, @Login Member loginMember) {
        Long basketId = basketService.addBasket(loginMember.getId(), storeId, form.getMenuOptionId(), form.getMenuSubOptionIds(), form.getCount());
        log.debug("장바구니 담기 성공 = {}", basketId);
        return "redirect:/stores/{storeId}/detail";
    }

    @ResponseBody
    @PostMapping("/basket/{basketId}/remove")
    public String removeBasket(@PathVariable Long basketId) {
        Long removeBasketId = basketService.removeBasketMenu(basketId);
        log.debug("removeBasketId = {}", removeBasketId);
        return "ok";
    }

    @ModelAttribute("basketAddForm")
    public BasketAddForm basketAddForm() {
        return new BasketAddForm();
    }

    private void headerInfo(Model model, Member loginMember) {
        List<NotificationDto> notifications = notificationService.findNotificationByMemberId(loginMember.getId());
        List<BasketMenuDto> basketMenus = basketService.findAllByMemberId(loginMember.getId());
        BasketDto basket = basketService.findBasketDto(loginMember.getId());

        model.addAttribute("notifications", notifications);
        model.addAttribute("basketMenus", basketMenus);
        model.addAttribute("basket", basket);
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

    private static List<Long> getMenuIds(List<MenuCategory> categories) {
        List<Long> ids = new ArrayList<>();
        for (MenuCategory category : categories) {
            ids.addAll(category.getMenus().stream()
                    .map(Menu::getId)
                    .toList());
        }
        return ids;
    }

    private static List<Long> getCategoryIds(List<MenuSubCategory> menuSubOptionCategorise) {
        return menuSubOptionCategorise.stream()
                .map(MenuSubCategory::getId)
                .toList();
    }
}
