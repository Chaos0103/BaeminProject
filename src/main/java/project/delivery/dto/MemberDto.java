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

    public MemberDto(String email, String password, String username, String birth, String phone, String nickname, AddressDto addressDto) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.birth = birth;
        this.phone = phone;
        this.nickname = nickname;
        this.addressDto = addressDto;
    }
}
