package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.MenuSubCategory;

import java.util.List;

public interface MenuSubCategoryRepository extends JpaRepository<MenuSubCategory, Long> {

    @Query("select msoc from MenuSubCategory msoc where msoc.menu.id in :menuIds")
    List<MenuSubCategory> findAllByMenuIds(@Param("menuIds") List<Long> menuIds);
}