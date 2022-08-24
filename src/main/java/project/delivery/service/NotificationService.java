package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Notification;

import java.util.List;

@Transactional(readOnly = true)
public interface NotificationService {

    @Transactional
    Long createNotification(Long memberId, Notification notification);

    List<Notification> findByMemberId(Long memberId);
}
