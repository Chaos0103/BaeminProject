package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.*;
import project.delivery.repository.MenuOptionRepository;
import project.delivery.repository.MenuRepository;
import project.delivery.repository.MenuSubOptionCategoryRepository;
import project.delivery.repository.MenuSubOptionRepository;
import project.delivery.service.MenuService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImplV0 implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuSubOptionCategoryRepository menuSubOptionCategoryRepository;
    private final MenuSubOptionRepository menuSubOptionRepository;
    private final MenuOptionRepository menuOptionRepository;

    @Override
    public List<Menu> findMenuByStoreId(Long storeId) {
        return menuRepository.findAllByStoreId(storeId);
    }

    @Override
    public List<MenuOption> findMenuOptionByMenuIds(List<Long> menuIds) {
        return menuOptionRepository.findAllByMenuIds(menuIds);
    }

    @Override
    public List<MenuSubOptionCategory> findMenuSubOptionCategory(List<Long> menuIds) {
        return menuSubOptionCategoryRepository.findAllByMenuIds(menuIds);
    }

    @Override
    public List<MenuSubOption> findMenuSubOption(List<Long> categoryIds) {
        return menuSubOptionRepository.findAllByMenuIds(categoryIds);
    }
}
