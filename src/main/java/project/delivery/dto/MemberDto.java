package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.Member;

import java.time.LocalDateTime;

@Data
public class MemberDto {

    private Long id;
    private String email;
    private String password;
    private String username;
    private String birth;
    private String phone;
    private String nickname;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private AddressDto addressDto;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.username = member.getUsername();
        this.birth = member.getBirth();
        this.phone = member.getPhone();
        this.nickname = member.getNickname();
        this.createdDate = member.getCreatedDate();
        this.lastModifiedDate = member.getLastModifiedDate();
        this.addressDto = new AddressDto(member.getAddress());
    }
}
