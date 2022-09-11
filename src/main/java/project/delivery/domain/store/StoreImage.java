package project.delivery.domain.store;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.delivery.domain.UploadFile;
import project.delivery.domain.store.Store;

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

    @Embedded
    private UploadFile uploadFile;

    public StoreImage(Store store, UploadFile uploadFile) {
        this.store = store;
        this.uploadFile = uploadFile;
        store.addStoreImage(this);
    }
}
