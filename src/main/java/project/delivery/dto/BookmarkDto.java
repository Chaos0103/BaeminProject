package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.Bookmark;

@Data
public class BookmarkDto {

    private Long bookmarkId;
    private Long storeId;
    private String storeFileName;
    private String storeName;
    private Float storeRating;
    private Integer storeReviewCount;
    private Integer minOrderPrice;
    private Integer minDeliveryTip;
    private Integer maxDeliveryTip;
    private String deliveryTime;
    private Boolean packingInfo;

    public BookmarkDto(Bookmark bookmark) {
        this.bookmarkId = bookmark.getId();
        this.storeId = bookmark.getStore().getId();
        this.storeFileName = bookmark.getStore().getStoreImages().stream()
                .filter(storeImage -> (!storeImage.isBanner())).findFirst().orElse(null)
                .getUploadFile().getStoreFileName();
        this.storeName = bookmark.getStore().getStoreName();
        this.storeRating = bookmark.getStore().getRating();
        this.storeReviewCount = bookmark.getStore().getReviewCount();
        this.minOrderPrice = bookmark.getStore().getDeliveryInfo().getMinOrderPrice();
        this.deliveryTime = bookmark.getStore().getDeliveryInfo().getDeliveryTime();
        this.packingInfo = bookmark.getStore().getPackingInfo() != null;

    }
}
