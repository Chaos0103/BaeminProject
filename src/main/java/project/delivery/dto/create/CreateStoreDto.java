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
}
