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
    List<MenuCategory> findMenuCategoryByStoreId(Long storeId);

    //가게배너사진 조회
    List<StoreImage> findStoreBannerImages(Long storeId);

    //주문금액 별 배달팁 조회
    List<DeliveryTipByAmount> findDeliveryTipByAmountByDeliveryId(Long deliveryId);

    //지역별 추가 배달팁 조회
    List<DeliveryTipByArea> findDeliveryTipByAreaByDeliveryId(Long deliveryId);

    Integer findDeliveryTip(Long storeId, Integer totalAmount);
}
