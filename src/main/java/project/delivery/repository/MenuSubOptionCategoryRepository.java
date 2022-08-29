package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.MenuSubOptionCategory;

import java.util.List;

public interface MenuSubOptionCategoryRepository extends JpaRepository<MenuSubOptionCategory, Long> {

    @Query("select msoc from MenuSubOptionCategory msoc where msoc.menu.id in :menuIds")
    List<MenuSubOptionCategory> findAllByMenuIds(@Param("menuIds") List<Long> menuIds);
}
