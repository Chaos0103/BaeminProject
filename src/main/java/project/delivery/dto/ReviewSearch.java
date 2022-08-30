package project.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewSearch {

    private Long storeId;
    private Boolean photo;
    private String type;
}
