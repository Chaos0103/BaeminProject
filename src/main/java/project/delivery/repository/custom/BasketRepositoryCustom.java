package project.delivery.repository.custom;

import project.delivery.domain.basket.BasketMenu;
import project.delivery.dto.BasketDto;
import project.delivery.dto.BasketMenuDto;

import java.util.List;

public interface BasketRepositoryCustom {

    List<BasketMenuDto> findBasketMenuByMemberId(Long memberId);

    List<BasketMenu> findBasketMenus(Long basketId);

    BasketDto findStoreInfo(Long memberId);

    BasketMenu findBasketMenuById(Long basketMenuId);
}
