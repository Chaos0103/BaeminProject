package project.delivery.dto;

import lombok.Data;

@Data
public class AddressDto {

    private String zipcode;
    private String mainAddress;
    private String detailAddress;

    public AddressDto(String zipcode, String mainAddress, String detailAddress) {
        this.zipcode = zipcode;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
    }
}
