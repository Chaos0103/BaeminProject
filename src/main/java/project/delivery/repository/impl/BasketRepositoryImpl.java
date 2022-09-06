package project.delivery.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.QStore;
import project.delivery.domain.QStoreImage;
import project.delivery.domain.basket.BasketMenu;
import project.delivery.dto.BasketDto;
import project.delivery.dto.BasketMenuDto;
import project.delivery.repository.custom.BasketRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.delivery.domain.QMenu.*;
import static project.delivery.domain.QMenuOption.*;
import static project.delivery.domain.QMenuSubOption.*;
import static project.delivery.domain.QStore.*;
import static project.delivery.domain.QStoreImage.*;
import static project.delivery.domain.basket.QBasket.*;
import static project.delivery.domain.basket.QBasketMenu.*;
import static project.delivery.domain.basket.QBasketSubOptionInfo.*;

public class BasketRepositoryImpl implements BasketRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BasketRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<BasketMenuDto> findBasketMenuByMemberId(Long memberId) {

        List<Long> ids = queryFactory
                .select(basketMenu.id)
                .from(basketMenu)
                .join(basketMenu.basket, basket)
                .where(basket.member.id.eq(memberId))
                .fetch();


        return queryFactory
                .select(Projections.fields(BasketMenuDto.class,
                        basketMenu.id.as("basketMenuId"),
                        menu.mainTitle.as("menuMainTitle"),
                        menu.uploadFile.storeFileName,
                        menuOption.optionName,
                        menuOption.price.as("optionPrice"),
                        basketMenu.orderPrice))
                .from(basketMenu)
                .join(basketMenu.menuOption, menuOption)
                .join(menuOption.menu, menu)
                .leftJoin(basketMenu.basketSubOptionInfos, basketSubOptionInfo)
                .join(basketSubOptionInfo.menuSubOption, menuSubOption)
                .where(basketMenu.id.in(ids))
                .fetch();
    }

    @Override
    public List<BasketMenu> findBasketMenus(Long basketId) {
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
    public BasketDto findStoreInfo(Long memberId) {
        return queryFactory
                .select(Projections.fields(BasketDto.class,
                        basket.id.as("basketId"),
                        store.storeName))
                .from(basket)
                .join(basket.store, store)
                .join(store.storeImages, storeImage)
                .where(
                        basket.member.id.eq(memberId),
                        storeImage.banner.isFalse()
                )
                .fetchOne();
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
}
