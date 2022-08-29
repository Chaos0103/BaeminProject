package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.Bookmark;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("select bm from Bookmark bm join fetch bm.store s where bm.member.id = :memberId")
    List<Bookmark> findByMemberId(@Param("memberId") Long memberId);
}
