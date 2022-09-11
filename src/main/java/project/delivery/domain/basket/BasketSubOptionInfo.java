package project.delivery.domain.basket;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.store.MenuSubOption;
import project.delivery.domain.TimeBaseEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasketSubOptionInfo extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "basket_sub_option_info_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "basket_menu_id")
    private BasketMenu basketMenu;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_sub_option_id")
    private MenuSubOption menuSubOption;

    public BasketSubOptionInfo(BasketMenu basketMenu, MenuSubOption menuSubOption) {
        this.basketMenu = basketMenu;
        this.menuSubOption = menuSubOption;
    }
}
