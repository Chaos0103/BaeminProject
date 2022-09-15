package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.delivery.domain.store.Store;
import project.delivery.repository.custom.StoreRepositoryCustom;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {

    Optional<Store> findByBusinessNumber(String businessNumber);
}
