package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.Notification;
import project.delivery.domain.NotificationType;

import java.time.LocalDateTime;

@Data
public class NotificationDto {

    private Long id;
    private String storeName;
    private String content;
    private NotificationType type;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public NotificationDto(Notification notification) {
        this.id = notification.getId();
        this.storeName = notification.getStoreName();
        this.content = notification.getContent();
        this.type = notification.getType();
        this.createdDate = notification.getCreatedDate();
        this.lastModifiedDate = notification.getLastModifiedDate();
    }
}
