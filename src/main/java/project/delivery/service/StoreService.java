package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.*;

import java.util.List;

@Transactional(readOnly = true)
public interface StoreService {

    @Transactional
    Long createNewStore(Store store);

    List<Store> findStoresByCategory(Category category);

    Store findStoreById(Long storeId);

    //카테고리 조회
    List<MenuCategory> findCategory(Long storeId);

    //가게배너사진 조회
    List<StoreImage> findStoreBannerImages(Long storeId);
}
