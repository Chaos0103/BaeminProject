package project.delivery.domain.basket;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.*;
import project.delivery.domain.member.Member;
import project.delivery.domain.store.Store;

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

    @OneToMany(mappedBy = "basket", orphanRemoval = true, cascade = CascadeType.ALL)
    List<BasketMenu> basketMenus = new ArrayList<>();

    public Basket(Member member, Store store, BasketMenu basketMenu) {
        this.member = member;
        this.store = store;
        basketMenu.addBasket(this);
        this.addBasketMenu(basketMenu);
    }

    //==연관관계 메서드==//
    public void addBasketMenu(BasketMenu basketMenu) {
        this.basketMenus.add(basketMenu);
    }
}
