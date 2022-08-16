package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.PayHistory;
import project.delivery.domain.TransactionType;

@Data
public class PayHistoryDto {

    private int price;
    private String content;
    private TransactionType type;

    public PayHistoryDto(int price, String content, TransactionType type) {
        this.price = price;
        this.content = content;
        this.type = type;
    }

    public PayHistoryDto(PayHistory payHistory) {
        this.price = payHistory.getPrice();
        this.content = payHistory.getContent();
        this.type = payHistory.getType();
    }
}
