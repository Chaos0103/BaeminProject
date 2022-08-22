package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends TimeBaseEntity {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @Column(updatable = false, nullable = false)
    private String storeName;
    @Enumerated(EnumType.STRING)
    @Column(updatable = false, nullable = false)
    private Category category;
    @Embedded
    private UploadFile uploadFile;
    @Column(nullable = false, length = 11)
    private String tel;
    @Lob
    @Column(nullable = false)
    private String introduction;
    @Column(nullable = false)
    private String tradeName;
    @Column(nullable = false)
    private String openTime;
    @Column(nullable = false)
    private String holiday;
    @Column(nullable = false)
    private String deliveryArea;
    @Lob
    @Column(nullable = false)
    private String deliveryTip;
    @Column(nullable = false, length = 20)
    private String representativeName;
    @Column(nullable = false)
    private String businessAddress;
    @Column(nullable = false)
    private String businessNumber;
    @Lob
    @Column(nullable = false)
    private String announcement;

    @Column(nullable = false)
    private float rating;
    @Column(nullable = false)
    private int reviewCount;
    @Column(nullable = false)
    private int commentCount;
    @Column(nullable = false)
    private int bookmarkCount;
    @Column(nullable = false)
    private int recentOrderCount;

    @OneToOne(mappedBy = "store", optional = false, cascade = CascadeType.ALL, fetch = LAZY)
    private DeliveryInfo deliveryInfo;

    public Store(String storeName, Category category, UploadFile uploadFile, String tel, String introduction, String tradeName, String openTime, String holiday, String deliveryArea, String deliveryTip, String representativeName, String businessAddress, String businessNumber, String announcement) {
        this.storeName = storeName;
        this.category = category;
        this.uploadFile = uploadFile;
        this.tel = tel;
        this.introduction = introduction;
        this.tradeName = tradeName;
        this.openTime = openTime;
        this.holiday = holiday;
        this.deliveryArea = deliveryArea;
        this.deliveryTip = deliveryTip;
        this.representativeName = representativeName;
        this.businessAddress = businessAddress;
        this.businessNumber = businessNumber;
        this.announcement = announcement;

        this.rating = 0.0f;
        this.reviewCount = 0;
        this.commentCount = 0;
        this.bookmarkCount = 0;
        this.recentOrderCount = 0;
    }

    //==연관관계 메서드==//
    public void addDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
}
