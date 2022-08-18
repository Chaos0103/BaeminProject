package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.Address;

@Data
public class AddressDto {

    private String zipcode;
    private String mainAddress;
    private String detailAddress;

    public AddressDto(Address address) {
        this.zipcode = address.getZipcode();
        this.mainAddress = address.getMainAddress();
        this.detailAddress = address.getDetailAddress();
    }
}
