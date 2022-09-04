package project.delivery.repository.custom;

import project.delivery.dto.NotificationDto;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepositoryCustom {

    /**
     *
     * @param memberId 회원 PK
     * @param period 조회 기간 : 3일
     * @return storeName, notificationType, createdDate
     */
    List<NotificationDto> findNotificationByMemberId(Long memberId, LocalDateTime period);
}
