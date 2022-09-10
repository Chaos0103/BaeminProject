package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.member.Member;
import project.delivery.domain.MenuOption;
import project.delivery.domain.MenuSubOption;
import project.delivery.domain.Store;
import project.delivery.domain.basket.Basket;
import project.delivery.domain.basket.BasketMenu;
import project.delivery.dto.BasketDto;
import project.delivery.dto.BasketMenuDto;
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
    public Long addBasket(Long memberId, Long storeId, Long menuOptionId, List<Long> menuSubOptionIds, Integer count) {
        Basket basket = findBasket(memberId);

        MenuOption menuOption = findMenuOption(menuOptionId);
        List<MenuSubOption> menuSubOptions = findMenuSubOptions(menuSubOptionIds);
        Integer orderPrice = getOrderPrice(menuOption, menuSubOptions);
        BasketMenu basketMenu = BasketMenu.createBasketMenu(menuOption, count, orderPrice, menuSubOptions);

        if (basket == null) {
            Member member = findMember(memberId);
            Store store = findStore(storeId);

            Basket savedBasket = basketRepository.save(new Basket(member, store, basketMenu));
            return savedBasket.getId();
        } else {
            basketMenu.addBasket(basket);
            basket.addBasketMenu(basketMenu);
            return basket.getId();
        }
    }

    @Override
    public Long removeBasketMenu(Long basketMenuId) {
        BasketMenu basketMenu = findBasketMenu(basketMenuId);
        if (basketMenu.getBasket().getBasketMenus().size() == 1) {
            basketRepository.delete(basketMenu.getBasket());
        } else {
            basketMenu.remove();
        }
        return basketMenuId;
    }

    private BasketMenu findBasketMenu(Long basketMenuId) {
        BasketMenu findBasketMenu = basketRepository.findBasketMenuById(basketMenuId);
        if (findBasketMenu == null) {
            throw new NoSuchException("등록되지 않은 장바구니입니다");
        }
        return findBasketMenu;
    }

    @Override
    public List<BasketMenuDto> findAllByMemberId(Long memberId) {
        return basketRepository.findBasketMenuByMemberId(memberId);
    }

    @Override
    public BasketDto findBasketDto(Long memberId) {
        return basketRepository.findStoreInfo(memberId);
    }

    @Override
    public Basket findBasketById(Long basketId) {
        return basketRepository.findBasketById(basketId);
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

    private Basket findBasket(Long memberId) {
        return basketRepository.findByMemberId(memberId);
    }
}
