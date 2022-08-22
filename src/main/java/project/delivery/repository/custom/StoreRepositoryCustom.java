package project.delivery.repository.custom;

import project.delivery.domain.Category;
import project.delivery.domain.Store;

import java.util.List;

public interface StoreRepositoryCustom {

    List<Store> findAllByCondition(Category category);
}
