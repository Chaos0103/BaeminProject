package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.dto.BasketDto;

@Transactional(readOnly = true)
public interface BasketService {

    Long addBasket(Long memberId, Long storeId, BasketDto basketDto);

    Long removeBasket(Long basketId);
}
