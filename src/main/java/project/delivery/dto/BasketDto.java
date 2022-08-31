package project.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketDto {

    private Long menuOptionId;
    private List<Long> menuSubOptionIds = new ArrayList<>();
    private Integer count;
}
