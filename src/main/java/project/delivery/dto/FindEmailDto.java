package project.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FindEmailDto {

    private Long id;
    private String email;
    private LocalDateTime createdDate;
}
