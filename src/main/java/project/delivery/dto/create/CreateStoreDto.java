package project.delivery.dto.create;

import lombok.Data;
import project.delivery.domain.Category;

@Data
public class CreateStoreDto {

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

    public CreateStoreDto(String storeName, Category category, String tel, String introduction, String tradeName, String openTime, String holiday, String deliveryArea, String deliveryTip, String representativeName, String businessAddress, String businessNumber, String announcement) {
        this.storeName = storeName;
        this.category = category;
        this.tel = tel;
        this.introduction = introduction;
        this.tradeName = tradeName;
        this.openTime = openTime;
        this.holiday = holiday;
        this.deliveryArea = deliveryArea;
        this.deliveryTip = deliveryTip;
        this.representativeName = representativeName;
        this.businessAddress = businessAddress;
        this.businessNumber = businessNumber;
        this.announcement = announcement;
    }
}
