package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.dto.NotificationDto;
import project.delivery.repository.NotificationRepository;
import project.delivery.service.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImplV0 implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<NotificationDto> findNotifications(Long memberId) {
        LocalDateTime period = LocalDateTime.now().minusDays(3);
        return notificationRepository.findNotifications(memberId, period).stream()
                .map(NotificationDto::new)
                .toList();
    }
}
