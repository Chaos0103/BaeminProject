package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Member;
import project.delivery.domain.MenuOption;
import project.delivery.domain.MenuSubOption;
import project.delivery.domain.Store;
import project.delivery.domain.basket.Basket;
import project.delivery.dto.BasketDto;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.*;
import project.delivery.service.BasketService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImplV0 implements BasketService {

    private final BasketRepository basketRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuSubOptionRepository menuSubOptionRepository;

    @Override
    public Long addBasket(Long memberId, Long storeId, BasketDto basketDto) {
        Member member = findMember(memberId);
        Store store = findStore(storeId);

        MenuOption menuOption = findMenuOption(basketDto.getMenuOptionId());
        List<MenuSubOption> menuSubOptions = findMenuSubOptions(basketDto.getMenuSubOptionIds());
        Integer orderPrice = getOrderPrice(menuOption, menuSubOptions);

        Basket basket = Basket.createBasket(member, store, menuOption, basketDto.getCount(), orderPrice, menuSubOptions);
        Basket savedBasket = basketRepository.save(basket);

        return savedBasket.getId();
    }

    @Override
    public Long removeBasket(Long basketId) {
        Basket basket = findBasket(basketId);
        basketRepository.delete(basket);
        return basketId;
    }

    private Member findMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            throw new NoSuchException("등록되지 않은 회원입니다");
        }
        return member;
    }

    private Store findStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElse(null);
        if (store == null) {
            throw new NoSuchException("등록되지 않은 매장입니다");
        }
        return store;
    }

    private MenuOption findMenuOption(Long menuOptionId) {
        MenuOption menuOption = menuOptionRepository.findById(menuOptionId).orElse(null);
        if (menuOption == null) {
            throw new NoSuchException("등록되지 않은 메뉴입니다");
        }
        return menuOption;
    }

    private List<MenuSubOption> findMenuSubOptions(List<Long> menuSubOptionIds) {
        return menuSubOptionRepository.findAllByIds(menuSubOptionIds);
    }

    private Integer getOrderPrice(MenuOption menuOption, List<MenuSubOption> menuSubOptions) {
        int orderPrice = menuOption.getPrice();
        for (MenuSubOption menuSubOption : menuSubOptions) {
            orderPrice += menuSubOption.getPrice();
        }
        return orderPrice;
    }

    private Basket findBasket(Long basketId) {
        Basket basket = basketRepository.findById(basketId).orElse(null);
        if (basket == null) {
            throw new NoSuchException("등록되지 않은 장바구니입니다");
        }
        return basket;
    }
}
