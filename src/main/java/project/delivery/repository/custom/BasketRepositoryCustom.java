package project.delivery.repository.custom;

import project.delivery.domain.basket.Basket;
import project.delivery.domain.basket.BasketMenu;

import java.util.List;

public interface BasketRepositoryCustom {

    List<BasketMenu> findBasketMenusdd(Long basketId);

    BasketMenu findBasketMenuById(Long basketMenuId);

    Basket findWithStore(Long memberId);

    List<BasketMenu> findBasketMenus(List<Long> ids);
}
