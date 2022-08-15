package project.delivery.dto;

import lombok.Data;

@Data
public class AddressDto {

    private String zipcode;
    private String mainAddress;
    private String detailAddress;
}
