package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.basket.Basket;
import project.delivery.dto.BasketDto;
import project.delivery.dto.BasketMenuDto;

import java.util.List;

@Transactional(readOnly = true)
public interface BasketService {

    @Transactional
    Long addBasket(Long memberId, Long storeId, Long menuOptionId, List<Long> menuSubOptionIds, Integer count);

    @Transactional
    Long removeBasketMenu(Long basketMenuId);

    Basket findBasketById(Long basketId);

    BasketDto findBasket(Long memberId);
}
