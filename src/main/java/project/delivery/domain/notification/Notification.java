package project.delivery.domain.notification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.TimeBaseEntity;
import project.delivery.domain.member.Member;

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
    private NotificationType type;

    public Notification(Member member, String storeName, NotificationType type) {
        this.member = member;
        this.storeName = storeName;
        this.type = type;
    }
}
