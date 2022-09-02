package project.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketMenuDto {

    private Long basketMenuId;
    private String menuMainTitle;
    private String storeFileName;
    private String optionName;
    private Integer optionPrice;
    private Integer orderPrice;
}
