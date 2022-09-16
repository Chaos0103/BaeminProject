package project.delivery.service.query.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.basket.Basket;
import project.delivery.domain.basket.BasketMenu;
import project.delivery.dto.BasketDto;
import project.delivery.dto.BasketMenuDto;
import project.delivery.repository.BasketRepository;
import project.delivery.service.query.BasketQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketQueryServiceImpl implements BasketQueryService {

    private final BasketRepository basketRepository;

    @Override
    public Basket findBasketById(Long basketId) {
        return basketRepository.findBasketById(basketId);
    }

    @Override
    public BasketDto findBasket(Long memberId) {
        Basket findBasket = basketRepository.findWithStore(memberId);
        List<Long> ids = findBasket.getBasketMenus().stream()
                .map(BasketMenu::getId)
                .toList();
        List<BasketMenu> basketMenus = basketRepository.findBasketMenus(ids);

        List<BasketMenuDto> basketMenuDtos = basketMenus.stream()
                .map(BasketMenuDto::new)
                .toList();
        return new BasketDto(findBasket, basketMenuDtos);
    }
}
