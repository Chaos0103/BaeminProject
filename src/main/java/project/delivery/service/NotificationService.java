package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.dto.NotificationDto;

import java.util.List;

@Transactional(readOnly = true)
public interface NotificationService {

    /**
     * 3일 전 알림까지 조회 가능
     * @param memberId
     * @return 회원의 알림 리스트
     */
    List<NotificationDto> findNotificationByMemberId(Long memberId);
}
