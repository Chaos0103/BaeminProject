package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.store.StoreImage;

import java.util.List;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {

    @Query("select si from StoreImage si where si.store.id = :storeId")
    List<StoreImage> findBannerByStoreId(@Param("storeId") Long storeId);
}
