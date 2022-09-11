package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.notification.NotificationType;

import java.time.LocalDateTime;

@Data
public class NotificationDto {

    private String storeName;
    private NotificationType type;
    private LocalDateTime createdDate;
}
