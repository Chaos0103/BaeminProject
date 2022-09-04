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

    List<BasketMenuDto> findAllByMemberId(Long memberId);

    BasketDto findBasketDto(Long memberId);

    Basket findBasketById(Long basketId);
}
