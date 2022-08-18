package project.delivery.dto.create;

import lombok.Data;

@Data
public class CreateMemberDto {

    private String email;
    private String password;
    private String username;
    private String birth;
    private String phone;
    private String nickname;
    private CreateAddressDto addressDto;

    public CreateMemberDto(String email, String password, String username, String birth, String phone, String nickname, CreateAddressDto addressDto) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.birth = birth;
        this.phone = phone;
        this.nickname = nickname;
        this.addressDto = addressDto;
    }
}
