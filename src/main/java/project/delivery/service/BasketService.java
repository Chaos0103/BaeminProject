package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BasketService {

    Long addBasket(Long memberId, Long storeId, Long menuOptionId, List<Long> menuSubOptionIds, Integer count);

    Long removeBasketMenu(Long basketMenuId);
}
