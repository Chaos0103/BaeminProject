package project.delivery.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.store.MenuOption;
import project.delivery.domain.store.MenuSubOption;
import project.delivery.domain.TimeBaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOrder extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "menu_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_option_id")
    private MenuOption menuOption;

    @Column(nullable = false)
    private Integer count;
    @Column(nullable = false)
    private Integer orderPrice;

    @OneToMany(mappedBy = "menuOrder", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SubOptionInfo> subOptionInfos = new ArrayList<>();

    public MenuOrder(MenuOption menuOption, Integer count, Integer orderPrice) {
        this.menuOption = menuOption;
        this.count = count;
        this.orderPrice = orderPrice;
    }

    //==생성 메서드==//
    public static MenuOrder createMenuOrder(MenuOption menuOption, int count, int orderPrice, List<MenuSubOption> MenuSubOptions) {
        MenuOrder menuOrder = new MenuOrder(menuOption, count, orderPrice);
        for (MenuSubOption menuSubOption : MenuSubOptions) {
            menuOrder.addSubOptionInfo(new SubOptionInfo(menuOrder, menuSubOption));
        }
        return menuOrder;
    }

    //==연관관계 메서드==//
    public void addSubOptionInfo(SubOptionInfo subOptionInfo) {
        this.subOptionInfos.add(subOptionInfo);
    }

    public void addOrder(Order order) {
        this.order = order;
    }
}
