package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.basket.Basket;
import project.delivery.domain.basket.BasketMenu;
import project.delivery.repository.custom.BasketRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.delivery.domain.QDeliveryInfo.*;
import static project.delivery.domain.QMenu.*;
import static project.delivery.domain.QMenuOption.*;
import static project.delivery.domain.QMenuSubOption.*;
import static project.delivery.domain.QPackingInfo.*;
import static project.delivery.domain.QStore.*;
import static project.delivery.domain.basket.QBasket.*;
import static project.delivery.domain.basket.QBasketMenu.*;
import static project.delivery.domain.basket.QBasketSubOptionInfo.*;

public class BasketRepositoryImpl implements BasketRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BasketRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<BasketMenu> findBasketMenusdd(Long basketId) {
        return queryFactory
                .selectFrom(basketMenu)
                .join(basketMenu.menuOption, menuOption)
                .join(menuOption.menu, menu)
                .leftJoin(basketMenu.basketSubOptionInfos, basketSubOptionInfo)
                .join(basketSubOptionInfo.menuSubOption, menuSubOption)
                .where(basketMenu.basket.id.eq(basketId))
                .fetch();
    }

    @Override
    public BasketMenu findBasketMenuById(Long basketMenuId) {
        return queryFactory
                .selectFrom(basketMenu)
                .join(basketMenu.basket, basket).fetchJoin()
                .leftJoin(basketMenu.basketSubOptionInfos, basketSubOptionInfo).fetchJoin()
                .where(basketMenu.id.eq(basketMenuId))
                .fetchOne();
    }

    @Override
    public Basket findWithStore(Long memberId) {
        return queryFactory
                .select(basket).distinct()
                .from(basket)
                .join(basket.store, store).fetchJoin()
                .join(store.deliveryInfo, deliveryInfo).fetchJoin()
                .join(store.packingInfo, packingInfo).fetchJoin()
                .join(basket.basketMenus, basketMenu).fetchJoin()
                .where(basket.member.id.eq(memberId))
                .fetchOne();
    }

    @Override
    public List<BasketMenu> findBasketMenus(List<Long> ids) {
        return queryFactory
                .select(basketMenu).distinct()
                .from(basketMenu)
                .join(basketMenu.menuOption, menuOption).fetchJoin()
                .join(menuOption.menu, menu).fetchJoin()
                .leftJoin(basketMenu.basketSubOptionInfos, basketSubOptionInfo).fetchJoin()
                .where(basketMenu.id.in(ids))
                .fetch();
    }
}
