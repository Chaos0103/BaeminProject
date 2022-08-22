package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.Notification;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select n from Notification n where n.member.id = :memberId and n.createdDate >= :date")
    List<Notification> findByMemberId(@Param("memberId") Long memberId, @Param("date") LocalDateTime date);
}
