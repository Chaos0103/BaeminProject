package project.delivery.domain.basket;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.store.MenuOption;
import project.delivery.domain.store.MenuSubOption;
import project.delivery.domain.TimeBaseEntity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasketMenu extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "basket_menu_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_option_id")
    private MenuOption menuOption;

    @Column(nullable = false)
    private Integer count;
    @Column(nullable = false)
    private Integer orderPrice;

    @OneToMany(mappedBy = "basketMenu", orphanRemoval = true, cascade = CascadeType.ALL)
    List<BasketSubOptionInfo> basketSubOptionInfos = new ArrayList<>();

    public BasketMenu(MenuOption menuOption, Integer count, Integer orderPrice) {
        this.menuOption = menuOption;
        this.count = count;
        this.orderPrice = orderPrice;
    }

    //==생성 메서드==//
    public static BasketMenu createBasketMenu(MenuOption menuOption, Integer count, Integer orderPrice, List<MenuSubOption> menuSubOptions) {
        BasketMenu basketMenu = new BasketMenu(menuOption, count, orderPrice);
        for (MenuSubOption menuSubOption : menuSubOptions) {
            basketMenu.addBasketSubOptionInfo(new BasketSubOptionInfo(basketMenu, menuSubOption));
        }
        return basketMenu;
    }

    //==연관관계 메서드==//
    public void addBasketSubOptionInfo(BasketSubOptionInfo basketSubOptionInfo) {
        this.basketSubOptionInfos.add(basketSubOptionInfo);
    }

    public void addBasket(Basket basket) {
        this.basket = basket;
    }

    //==비즈니스 로직==//
    public void remove() {
        this.basket.getBasketMenus().remove(this);
        this.basket = null;
    }
}
