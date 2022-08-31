package project.delivery.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.Member;
import project.delivery.domain.TimeBaseEntity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "ORDERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Enumerated(EnumType.STRING)
    private ReceiptType receiptType;
    @Column(updatable = false, nullable = false)
    private Boolean disposable;
    @Column(updatable = false, nullable = false)
    private Boolean sideDish;
    @Column(updatable = false)
    private String requirement;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<MenuOrder> menuOrders = new ArrayList<>();

    public Order(Member member, Delivery delivery, ReceiptType receiptType, Boolean disposable, Boolean sideDish, String requirement) {
        this.member = member;
        this.delivery = delivery;
        this.receiptType = receiptType;
        this.disposable = disposable;
        this.sideDish = sideDish;
        this.requirement = requirement;
        this.status = OrderStatus.ORDER;
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, ReceiptType receiptType, Boolean disposable, Boolean sideDish, String requirement, List<MenuOrder> menuOrders) {
        Order order = new Order(member, delivery, receiptType, disposable, sideDish, requirement);
        for (MenuOrder menuOrder : menuOrders) {
            order.addMenuOrder(menuOrder);
        }
        return order;
    }

    //==연관관계 메서드==//
    public void addMenuOrder(MenuOrder menuOrder) {
        this.menuOrders.add(menuOrder);
        menuOrder.addOrder(this);
    }
}
