package project.delivery.domain.basket;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "basket_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_option_id")
    private MenuOption menuOption;

    @Column(nullable = false)
    private Integer count;
    @Column(nullable = false)
    private Integer orderPrice;

    @OneToMany(mappedBy = "basket", orphanRemoval = true, cascade = CascadeType.ALL)
    List<BasketSubOptionInfo> basketSubOptionInfos = new ArrayList<>();

    public Basket(Member member, Store store, MenuOption menuOption, Integer count, Integer orderPrice) {
        this.member = member;
        this.store = store;
        this.menuOption = menuOption;
        this.count = count;
        this.orderPrice = orderPrice;
    }

    //==생성 메서드==//
    public static Basket createBasket(Member member, Store store, MenuOption menuOption, Integer count, Integer orderPrice, List<MenuSubOption> MenuSubOptions) {
        Basket basket = new Basket(member, store, menuOption, count, orderPrice);
        for (MenuSubOption menuSubOption : MenuSubOptions) {
            basket.addBasketSubOptionInfo(new BasketSubOptionInfo(basket, menuSubOption));
        }
        return basket;
    }

    //==연관관계 메서드==//
    public void addBasketSubOptionInfo(BasketSubOptionInfo basketSubOptionInfo) {
        this.basketSubOptionInfos.add(basketSubOptionInfo);
    }
}
