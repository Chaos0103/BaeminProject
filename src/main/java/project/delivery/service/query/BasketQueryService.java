package project.delivery.service.query;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.basket.Basket;
import project.delivery.dto.BasketDto;

@Transactional(readOnly = true)
public interface BasketQueryService {

    Basket findBasketById(Long basketId);

    BasketDto findBasket(Long memberId);
}
