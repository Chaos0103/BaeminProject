package project.delivery.repository.custom;

import org.springframework.data.repository.query.Param;
import project.delivery.domain.store.Category;
import project.delivery.domain.store.DeliveryInfo;
import project.delivery.domain.store.Store;

import java.util.List;

public interface StoreRepositoryCustom {

    List<Store> findStores(Category category);

    Store findStoreDetail(Long storeId);

    Integer findDeliveryTip(Long storeId, Integer totalAmount);

    DeliveryInfo findDeliveryInfo(Long storeId);
}
