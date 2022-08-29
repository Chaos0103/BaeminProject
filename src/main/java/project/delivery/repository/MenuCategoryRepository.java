package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.MenuCategory;

import java.util.List;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

    @Query("select mc from MenuCategory mc where mc.store.id = :storeId order by mc.priority asc")
    List<MenuCategory> findAllByStoreId(@Param("storeId") Long storeId);
}
