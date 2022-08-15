package project.delivery.dto;

import lombok.Data;

@Data
public class MemberDto {

    private String email;
    private String password;
    private String username;
    private String birth;
    private String phone;
    private String nickname;
    private AddressDto addressDto;
}
