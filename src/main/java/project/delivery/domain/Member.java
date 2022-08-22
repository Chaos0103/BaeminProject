package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(updatable = false, nullable = false, length = 50)
    private String email;
    @Column(nullable = false, length = 16)
    private String password;
    @Column(updatable = false, nullable = false, length = 20)
    private String username;
    @Column(updatable = false, nullable = false, length = 8)
    private String birth;
    @Column(nullable = false, length = 11)
    private String phone;
    @Column(nullable = false, length = 10)
    private String nickname;
    @Enumerated(EnumType.STRING)
    private MemberGrade grade;
    @Embedded
    private Address address;

    @OneToOne(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL, fetch = LAZY)
    private Pay pay;

    @OneToOne(mappedBy = "member",orphanRemoval = true, cascade = CascadeType.ALL, fetch = LAZY)
    private Point point;

    public Member(String email, String password, String username, String birth, String phone, String nickname, Address address) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.birth = birth;
        this.phone = phone;
        this.nickname = nickname;
        this.address = address;

        this.grade = MemberGrade.BASIC;
    }

    //==연관관계 메서드==//
    public void addPay(Pay pay) {
        this.pay = pay;
    }

    public void addPoint(Point point) {
        this.point = point;
    }
    //==비즈니스 로직==//
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changePhone(String phone) {
        this.phone = phone;
    }

    public void changeAddress(Address address) {
        this.address = address;
    }
}
