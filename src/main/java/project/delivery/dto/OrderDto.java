package project.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.delivery.domain.order.Delivery;
import project.delivery.domain.order.ReceiptType;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Delivery delivery;
    private ReceiptType receiptType;
    private Boolean disposable;
    private Boolean sideDish;
    private String requirement;
    List<OrderInfoDto> orderInfos = new ArrayList<>();
}
