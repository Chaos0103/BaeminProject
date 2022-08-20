package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.Pay;
import project.delivery.domain.PayHistory;

import java.util.ArrayList;
import java.util.List;

@Data
public class PayDto {

    private Long id;
    private MemberDto memberDto;
    private String password;
    private int money;
    private List<PayHistoryDto> payHistoryDtos = new ArrayList<>();

    public PayDto(Pay pay) {
        this.id = pay.getId();
        this.memberDto = new MemberDto(pay.getMember());
        this.password = pay.getPassword();
        this.money = pay.getMoney();
        for (PayHistory payHistory : pay.getPayHistories()) {
            payHistoryDtos.add(new PayHistoryDto(payHistory));
        }
    }
}
