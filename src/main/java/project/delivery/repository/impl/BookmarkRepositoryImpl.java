package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.member.Bookmark;
import project.delivery.repository.custom.BookmarkRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.delivery.domain.member.QBookmark.*;
import static project.delivery.domain.store.QDeliveryInfo.*;
import static project.delivery.domain.store.QPackingInfo.*;
import static project.delivery.domain.store.QStore.*;
import static project.delivery.domain.store.QStoreImage.*;

public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BookmarkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Bookmark> findBookmarks(Long memberId) {
        return queryFactory
                .select(bookmark).distinct()
                .from(bookmark)
                .join(bookmark.store, store).fetchJoin()
                .join(store.packingInfo, packingInfo).fetchJoin()
                .join(store.deliveryInfo, deliveryInfo).fetchJoin()
                .join(store.storeImages, storeImage).fetchJoin()
                .where(
                        bookmark.member.id.eq(memberId)
                )
                .fetch();
    }
}
