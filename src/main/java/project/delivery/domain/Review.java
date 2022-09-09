package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.order.Order;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    @Column(nullable = false)
    private int rating;

    @Lob
    private String content;
    private UploadFile uploadFile;

    public Review(Member member, Order order, int rating, String content, UploadFile uploadFile) {
        this.member = member;
        this.order = order;
        this.rating = rating;
        this.content = content;
        this.uploadFile = uploadFile;
    }
}
