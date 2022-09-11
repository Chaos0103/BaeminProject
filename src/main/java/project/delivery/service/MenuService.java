package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.store.Menu;
import project.delivery.domain.store.MenuOption;
import project.delivery.domain.store.MenuSubCategory;
import project.delivery.domain.store.MenuSubOption;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuService {

    //가게 메뉴 가져오기
    List<Menu> findMenuByStoreId(Long storeId);

    //메뉴 옵션 가져오기
    List<MenuOption> findMenuOptionByMenuIds(List<Long> menuIds);

    //서브카테고리이름 조회
    List<MenuSubCategory> findMenuSubOptionCategory(List<Long> menuIds);

    //서브메뉴
    List<MenuSubOption> findMenuSubOption(List<Long> categoryIds);
}
