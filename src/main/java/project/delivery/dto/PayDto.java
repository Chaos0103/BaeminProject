package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.Pay;

import java.util.ArrayList;
import java.util.List;

@Data
public class PayDto {

    private Long id;
    private String password;
    private Integer money;
    private List<PayHistoryDto> payHistories = new ArrayList<>();

    public PayDto(Pay pay) {
        this.id = pay.getId();
        this.password = pay.getPassword();
        this.money = pay.getMoney();
        this.payHistories = pay.getPayHistories().stream()
                .map(PayHistoryDto::new)
                .toList();
    }
}
