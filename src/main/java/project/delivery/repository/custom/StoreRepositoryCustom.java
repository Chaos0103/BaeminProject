package project.delivery.repository.custom;

import project.delivery.domain.store.Category;
import project.delivery.domain.store.Store;

import java.util.List;

public interface StoreRepositoryCustom {

    List<Store> findAllByCondition(Category category);

    Integer findDeliveryTip(Long storeId, Integer totalAmount);
}
