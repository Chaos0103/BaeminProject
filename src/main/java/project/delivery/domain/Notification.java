package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String storeName;
    private String content;
    private NotificationType type;

    public Notification(String storeName, String content, NotificationType type) {
        this.storeName = storeName;
        this.content = content;
        this.type = type;
    }

    //==연관관계 메서드==//
    public void addMember(Member member) {
        this.member = member;
    }
}
