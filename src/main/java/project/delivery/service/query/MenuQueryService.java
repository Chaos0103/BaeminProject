package project.delivery.service.query;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.store.MenuOption;
import project.delivery.domain.store.MenuSubCategory;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuQueryService {

    //메뉴 옵션 가져오기
    List<MenuOption> findMenuOptionByMenuIds(List<Long> menuIds);

    //서브카테고리이름 조회
    List<MenuSubCategory> findMenuSubOptionCategory(List<Long> menuIds);
}
