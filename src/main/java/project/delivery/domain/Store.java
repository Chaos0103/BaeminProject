package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    @Column(nullable = false, length = 13)
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
    @Column(nullable = false, length = 20)
    private String representativeName;
    @Embedded
    private Address businessAddress;
    @Column(nullable = false)
    private String businessNumber;
    @Embedded
    private Announcement announcement;
    @Lob
    @Column(nullable = false)
    private String countryOfPlace;

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

    @OneToOne(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.ALL, fetch = LAZY)
    private DeliveryInfo deliveryInfo;

    @OneToOne(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.ALL, fetch = LAZY)
    private PackingInfo packingInfo;

    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<StoreImage> storeImages = new ArrayList<>();

    public Store(String storeName, Category category, String tel, String introduction, String tradeName, String openTime, String holiday, String deliveryArea, String representativeName, Address businessAddress, String businessNumber, Announcement announcement, String countryOfPlace) {
        this.storeName = storeName;
        this.category = category;
        this.tel = tel;
        this.introduction = introduction;
        this.tradeName = tradeName;
        this.openTime = openTime;
        this.holiday = holiday;
        this.deliveryArea = deliveryArea;
        this.representativeName = representativeName;
        this.businessAddress = businessAddress;
        this.businessNumber = businessNumber;
        this.announcement = announcement;
        this.countryOfPlace = countryOfPlace;

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

    public void addPackingInfo(PackingInfo packingInfo) {
        this.packingInfo = packingInfo;
    }

    public void addStoreImage(StoreImage storeImage) {
        this.storeImages.add(storeImage);
    }

    //==비즈니스 로직==//
    public void updateRating(float rating) {
        this.rating = rating;
    }

    public void addReviewCount() {
        this.reviewCount++;
    }

    public void removeReviewCount() {
        this.reviewCount--;
    }
}
