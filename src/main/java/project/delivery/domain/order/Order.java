package project.delivery.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.member.Member;
import project.delivery.domain.Store;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(updatable = false, nullable = false, length = 10)
    private String orderNumber;
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
    @Column(nullable = false)
    private Boolean reviewCreationStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<MenuOrder> menuOrders = new ArrayList<>();

    public Order(Member member, Store store, Delivery delivery, Payment payment, String orderNumber, ReceiptType receiptType, Boolean disposable, Boolean sideDish, String requirement) {
        this.member = member;
        this.store = store;
        this.delivery = delivery;
        this.payment = payment;
        this.orderNumber = orderNumber;
        this.receiptType = receiptType;
        this.disposable = disposable;
        this.sideDish = sideDish;
        this.requirement = requirement;
        this.status = OrderStatus.ORDER;
        this.reviewCreationStatus = false;
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Store store, Delivery delivery, Payment payment, String orderNumber, ReceiptType receiptType, Boolean disposable, Boolean sideDish, String requirement, List<MenuOrder> menuOrders) {
        Order order = new Order(member, store, delivery, payment, orderNumber, receiptType, disposable, sideDish, requirement);
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

    //==비즈니스 메서드==//
    public void changeReviewCreationStatus() {
        this.reviewCreationStatus = true;
    }
}
