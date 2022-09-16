package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.basket.BasketMenu;

@Data
public class BasketMenuDto {

    private Long basketMenuId;
    private String menuMainTitle;
    private String menuStoreFileName;
    private String optionName;
    private Integer optionPrice;
    private Integer orderPrice;

    public BasketMenuDto(BasketMenu basketMenu) {
        this.basketMenuId = basketMenu.getId();
        this.menuMainTitle = basketMenu.getMenuOption().getMenu().getMainTitle();
        this.menuStoreFileName = basketMenu.getMenuOption().getMenu().getUploadFile().getStoreFileName();
        this.optionName = basketMenu.getMenuOption().getOptionName();
        this.optionPrice = basketMenu.getMenuOption().getPrice();
        this.orderPrice = basketMenu.getOrderPrice();
    }
}
