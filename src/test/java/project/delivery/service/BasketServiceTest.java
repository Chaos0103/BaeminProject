package project.delivery.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.*;
import project.delivery.domain.basket.Basket;
import project.delivery.dto.BasketMenuDto;
import project.delivery.repository.BasketRepository;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.MenuOptionRepository;
import project.delivery.repository.StoreRepository;

@SpringBootTest
@Transactional
class BasketServiceTest {

    @Autowired BasketService basketService;
    @Autowired BasketRepository basketRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired StoreRepository storeRepository;
    @Autowired MenuOptionRepository menuOptionRepository;

    @Test
    @DisplayName("장바구니 담기")
    void addBasket() {
        //create Member
        Member member = createMember();
        Store store = createStore();
        //MenuOption 생성
        MenuOption menuOption = new MenuOption(null, "menuOption", 10000);
        menuOptionRepository.save(menuOption);

//        BasketMenuDto basketDto = new BasketMenuDto(menuOption.getId(), null, 2);
//
//        Long basketId = basketService.addBasket(member.getId(), store.getId(), basketDto);
//
//        Basket basket = basketRepository.findById(basketId).get();
//        assertThat(basket.getOrderPrice() * basket.getCount()).isEqualTo(20000);
//        assertThat(basket.getMenuOption().getId()).isEqualTo(menuOption.getId());
//        assertThat(basket.getBasketSubOptionInfos().size()).isEqualTo(0);
    }

    private Member createMember() {
        Address address = new Address("12345", "mainAddress", "detailAddress");
        Member member = new Member("test@test.com", "test1!", "user", "20010101", "01011111111", "tester", address);
        return memberRepository.save(member);
    }

    private Store createStore() {
        Store store = new Store("test", Category.CHICKEN, "0", "0", "0", "0", "0", "0", "0", "0", "0", new Announcement("0"), "0");
        return storeRepository.save(store);
    }
}