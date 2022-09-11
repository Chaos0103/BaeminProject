package project.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.delivery.domain.store.MenuSubOption;

import java.util.List;

public interface MenuSubOptionRepository extends JpaRepository<MenuSubOption, Long> {

    @Query("select mso from MenuSubOption mso where mso.category.id in :categoryIds")
    List<MenuSubOption> findAllByMenuIds(@Param("categoryIds") List<Long> categoryIds);

    @Query("select mso from MenuSubOption mso where mso.id in :ids")
    List<MenuSubOption> findAllByIds(@Param("ids") List<Long> ids);
}
