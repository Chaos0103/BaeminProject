package project.delivery.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.delivery.domain.member.Bookmark;
import project.delivery.dto.BookmarkDto;
import project.delivery.repository.custom.BookmarkRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static project.delivery.domain.QBookmark.*;
import static project.delivery.domain.QDeliveryInfo.*;
import static project.delivery.domain.QPackingInfo.*;
import static project.delivery.domain.QStore.*;
import static project.delivery.domain.QStoreImage.*;

public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BookmarkRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<BookmarkDto> findBookmarksByMemberId(Long memberId) {
        List<Bookmark> bookmarks = queryFactory
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

        return bookmarks.stream()
                .map(BookmarkDto::new)
                .toList();
    }
}
