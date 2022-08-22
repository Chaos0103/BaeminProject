package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.Store;
import project.delivery.repository.custom.StoreRepositoryCustom;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {
    Optional<Store> findByBusinessNumber(String businessNumber);

    @Query("select s from Store s join fetch s.deliveryInfo where s.id = :storeId")
    Optional<Store> findStore(@Param("storeId") Long storeId);
}
