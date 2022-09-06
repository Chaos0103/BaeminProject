package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.Bookmark;
import project.delivery.repository.custom.BookmarkRepositoryCustom;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {

//    @Query("select distinct bm from Bookmark bm join fetch bm.store s join fetch s.packingInfo join fetch s.deliveryInfo join fetch s.storeImages si where bm.member.id = :memberId and si.banner = false")
//    List<Bookmark> findBookmarksByMemberId(@Param("memberId") Long memberId);
}
