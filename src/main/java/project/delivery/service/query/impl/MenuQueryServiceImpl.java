package project.delivery.service.query.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.store.MenuOption;
import project.delivery.domain.store.MenuSubCategory;
import project.delivery.repository.MenuOptionRepository;
import project.delivery.repository.MenuSubCategoryRepository;
import project.delivery.service.query.MenuQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuQueryServiceImpl implements MenuQueryService {

    private final MenuSubCategoryRepository menuSubOptionCategoryRepository;
    private final MenuOptionRepository menuOptionRepository;

    @Override
    public List<MenuOption> findMenuOptionByMenuIds(List<Long> menuIds) {
        return menuOptionRepository.findAllByMenuIds(menuIds);
    }

    @Override
    public List<MenuSubCategory> findMenuSubOptionCategory(List<Long> menuIds) {
        return menuSubOptionCategoryRepository.findAllByMenuIds(menuIds);
    }
}
