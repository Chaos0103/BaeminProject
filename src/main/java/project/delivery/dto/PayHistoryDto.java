package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.pay.PayHistory;
import project.delivery.domain.pay.TransactionType;

import java.time.LocalDateTime;

@Data
public class PayHistoryDto {

    private Long id;
    private Integer price;
    private String content;
    private TransactionType type;
    private LocalDateTime createdDate;

    public PayHistoryDto(PayHistory payHistory) {
        this.id = payHistory.getId();
        this.price = payHistory.getPrice();
        this.content = payHistory.getContent();
        this.type = payHistory.getType();
        this.createdDate = payHistory.getCreatedDate();
    }
}
