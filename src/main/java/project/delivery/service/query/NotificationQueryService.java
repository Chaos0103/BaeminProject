package project.delivery.service.query;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.dto.NotificationDto;

import java.util.List;

@Transactional(readOnly = true)
public interface NotificationQueryService {

    /**
     * 3일 전 알림까지 조회하는 로직
     * @param memberId 조회할 회원의 id
     * @return NotificationDto
     */
    List<NotificationDto> findNotifications(Long memberId);
}
