package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Category;
import project.delivery.domain.Store;

import java.util.List;

@Transactional(readOnly = true)
public interface StoreService {

    @Transactional
    Long createNewStore(Store store);

    List<Store> searchStores(Category category);

    Store detailStore(Long storeId);
}
