package project.delivery.dto;

import lombok.Data;

@Data
public class PointDto {

    private Long id;
    private Integer totalPoint;
    private Integer removePoint;
    private Integer balance;
}
