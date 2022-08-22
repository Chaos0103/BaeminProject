package project.delivery.dto.create;

import lombok.Data;
import project.delivery.domain.NotificationType;

@Data
public class CreateNotificationDto {

    private String storeName;
    private String content;
    private NotificationType type;

    public CreateNotificationDto(String storeName, String content, NotificationType type) {
        this.storeName = storeName;
        this.content = content;
        this.type = type;
    }
}
