package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.store.Category;
import project.delivery.domain.store.Store;
import project.delivery.repository.custom.StoreRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {

    Optional<Store> findByBusinessNumber(String businessNumber);

    @Query("select distinct s from Store s join fetch s.deliveryInfo join fetch s.storeImages left join fetch s.packingInfo where s.id = :storeId")
    Store findDetailByStoreId(@Param("storeId") Long storeId);

    @Query("select distinct s from Store s join fetch s.deliveryInfo left join fetch s.packingInfo join fetch s.storeImages where s.category = :category")
    List<Store> findAllByCategory(@Param("category") Category category);
}
