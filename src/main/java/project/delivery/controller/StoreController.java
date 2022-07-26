package project.delivery.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.controller.form.BasketAddForm;
import project.delivery.domain.member.Review;
import project.delivery.domain.store.*;
import project.delivery.dto.LoginMember;
import project.delivery.dto.ReviewSearch;
import project.delivery.login.Login;
import project.delivery.service.*;
import project.delivery.service.query.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final GlobalInformation globalInformation;

    private final StoreQueryService storeQueryService;
    private final BookmarkService bookmarkService;
    private final BasketService basketService;

    private final MenuQueryService menuQueryService;
    private final ReviewQueryService reviewQueryService;

    /**
     * @URL: localhost:8080/stores
     */
    @GetMapping
    public String stores(@RequestParam Category category, @Login LoginMember loginMember, Model model) {
        globalInformation.headerInfo(loginMember, model);

        List<StoreDto> stores = storeQueryService.findStores(category).stream()
                .map(StoreDto::new)
                .toList();
        model.addAttribute("stores", stores);

        log.debug("category={}", category);
        return "/stores/stores";
    }

    @Data
    static class StoreDto {
        private Long storeId;
        private String storeFileName;
        private String storeName;
        private float rating;
        private int reviewCount;
        private int minOrderPrice;
        private String deliveryTime;
        private boolean packingInfo;

        public StoreDto(Store store) {
            this.storeId = store.getId();
            this.storeFileName = store.getStoreIcon().getStoreFileName();
            this.storeName = store.getStoreName();
            this.rating = store.getRating();
            this.reviewCount = store.getReviewCount();
            this.minOrderPrice = store.getDeliveryInfo().getMinOrderPrice();
            this.deliveryTime = store.getDeliveryInfo().getDeliveryTime();
            this.packingInfo = store.getPackingInfo() != null;
        }
    }

    /**
     * @URL: localhost:8080/stores/{storeId}/detail
     */
    @GetMapping("/{storeId}/detail")
    public String store(@PathVariable Long storeId, Model model, @Login LoginMember loginMember) {
        globalInformation.headerInfo(loginMember, model);

        Store store = storeQueryService.findStoreDetail(storeId);
        List<DeliveryTipByAmount> deliveryTipByAmounts = storeQueryService.findDeliveryTipByAmountByDeliveryId(store.getDeliveryInfo().getId());
        List<DeliveryTipByArea> deliveryTipByAreas = storeQueryService.findDeliveryTipByAreaByDeliveryId(store.getDeliveryInfo().getId());
        List<StoreImage> storeImages = storeQueryService.findStoreImages(storeId);

        //MenuCategory join fetch Menu
        List<MenuCategory> categories = storeQueryService.findMenuCategoryByStoreId(storeId);
        //Menu PK 조회
        List<Long> menuIds = getMenuIds(categories);
        List<MenuOption> menuOptions = menuQueryService.findMenuOptionByMenuIds(menuIds);
        List<MenuSubCategory> menuSubOptionCategorise = menuQueryService.findMenuSubOptionCategory(menuIds);

        List<Review> reviews = reviewQueryService.findAll(new ReviewSearch(storeId, false, "recent"));

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
        List<Review> reviews = reviewQueryService.findAll(search);
        model.addAttribute("reviews", reviews);
        return "/stores/detail :: #review";
    }


    @PostMapping("/{storeId}/bookmark")
    public String addBookmark(@PathVariable Long storeId, @Login LoginMember loginMember) {
        bookmarkService.addBookmark(loginMember.getId(), storeId);
        return "redirect:/stores/{storeId}/detail";
    }

    @PostMapping("/{storeId}/basket")
    public String addBasket(@PathVariable Long storeId, BasketAddForm form, @Login LoginMember loginMember) {
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
}
