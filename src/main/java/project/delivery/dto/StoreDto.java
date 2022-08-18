package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.Category;
import project.delivery.domain.Store;

@Data
public class StoreDto {
    private Long id;
    private String storeName;
    private Category category;
    private String tel;
    private String introduction;
    private String tradeName;
    private String openTime;
    private String holiday;
    private String deliveryArea;
    private String deliveryTip;
    private String representativeName;
    private String businessAddress;
    private String businessNumber;
    private String announcement;
    private float rating;
    private int reviewCount;
    private int commentCount;
    private int bookmarkCount;
    private int recentOrderCount;

    public StoreDto(Store store) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.category = store.getCategory();
        this.tel = store.getTel();
        this.introduction = store.getIntroduction();
        this.tradeName = store.getTradeName();
        this.openTime = store.getOpenTime();
        this.holiday = store.getHoliday();
        this.deliveryArea = store.getDeliveryArea();
        this.deliveryTip = store.getDeliveryTip();
        this.representativeName = store.getRepresentativeName();
        this.businessAddress = store.getBusinessAddress();
        this.businessNumber = store.getBusinessNumber();
        this.announcement = store.getAnnouncement();
        this.rating = store.getRating();
        this.reviewCount = store.getReviewCount();
        this.commentCount = store.getCommentCount();
        this.bookmarkCount = store.getBookmarkCount();
        this.recentOrderCount = store.getRecentOrderCount();
    }
}
