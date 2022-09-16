package project.delivery.dto;

import lombok.Data;
import project.delivery.domain.Address;
import project.delivery.domain.member.Member;
import project.delivery.domain.member.MemberGrade;

@Data
public class LoginMember {

    private Long id;
    private String email;
    private String password;
    private String username;
    private String birth;
    private String phone;
    private String nickname;
    private MemberGrade grade;
    private Address address;

    public LoginMember(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.username = member.getUsername();
        this.birth = member.getBirth();
        this.phone = member.getPhone();
        this.nickname = member.getNickname();
        this.grade = member.getGrade();
        this.address = member.getAddress();
    }
}
