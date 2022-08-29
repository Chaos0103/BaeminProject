package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.Menu;
import project.delivery.domain.MenuCategory;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m join fetch m.category c where c.store.id = :storeId")
    List<Menu> findAllByStoreId(@Param("storeId") Long storeId);
}
