package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.MenuOption;

import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {

    @Query("select mo from MenuOption mo where mo.menu.id in :menuIds")
    List<MenuOption> findAllByMenuIds(@Param("menuIds") List<Long> menuIds);
}
