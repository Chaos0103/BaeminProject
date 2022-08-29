package project.delivery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreImage {

    @Id @GeneratedValue
    @Column(name = "store_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private boolean banner;
    @Embedded
    private UploadFile uploadFile;

    public StoreImage(Store store, boolean banner, UploadFile uploadFile) {
        this.store = store;
        this.banner = banner;
        this.uploadFile = uploadFile;
        store.addStoreImage(this);
    }
}
