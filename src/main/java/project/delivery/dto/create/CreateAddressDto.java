package project.delivery.dto.create;

import lombok.Data;

@Data
public class CreateAddressDto {

    private String zipcode;
    private String mainAddress;
    private String detailAddress;

    public CreateAddressDto(String zipcode, String mainAddress, String detailAddress) {
        this.zipcode = zipcode;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
    }
}
