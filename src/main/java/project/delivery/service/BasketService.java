package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.basket.Basket;
import project.delivery.dto.BasketDto;

import java.util.List;

@Transactional(readOnly = true)
public interface BasketService {

    @Transactional
    Long addBasket(Long memberId, Long storeId, BasketDto basketDto);

    @Transactional
    Long removeBasket(Long basketId);

    List<Basket> findAllByMemberId(Long memberId);
}
