package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.dto.NotificationDto;
import project.delivery.dto.create.CreateNotificationDto;

import java.util.List;

@Transactional(readOnly = true)
public interface NotificationService {

    @Transactional
    Long createNotification(Long memberId, CreateNotificationDto createNotificationDto);

    List<NotificationDto> findByMemberId(Long memberId);
}
