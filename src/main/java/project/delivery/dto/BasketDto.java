package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.basket.Basket;

import java.util.List;

@Data
public class BasketDto {

    private Long basketId;
    private String storeName;
    private String storeFileName;
    private List<BasketMenuDto> basketMenus;

    public BasketDto(Basket basket, List<BasketMenuDto> basketMenus) {
        this.basketId = basket.getId();
        this.storeName = basket.getStore().getStoreName();
        this.storeFileName = basket.getStore().getStoreIcon().getStoreFileName();
        this.basketMenus = basketMenus;
    }
}
