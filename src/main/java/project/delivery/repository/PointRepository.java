package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.Point;
import project.delivery.repository.custom.PointRepositoryCustom;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long>, PointRepositoryCustom {

    @Query("select p from Point p join fetch p.member m where m.id = :memberId")
    Optional<Point> findByMemberId(@Param("memberId") Long memberId);

    @Query("select p.totalPoint from Point p where p.member.id = :memberId")
    Integer findTotalPoint(@Param("memberId") Long memberId);
}
