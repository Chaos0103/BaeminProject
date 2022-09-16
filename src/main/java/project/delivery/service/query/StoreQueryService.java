package project.delivery.service.query;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.store.*;

import java.util.List;

@Transactional(readOnly = true)
public interface StoreQueryService {

    /**
     * 카테고리 별 매장을 조회하는 로직
     * @param category 조회할 카테고리
     * @return 매장 목록
     */
    List<Store> findStores(Category category);

    /**
     * 매장에 관련된 정보를 조회하는 로직
     * @param storeId 조회할 매장의 id
     * @return 매장 정보
     */
    Store findStoreDetail(Long storeId);

    //카테고리 조회
    List<MenuCategory> findMenuCategoryByStoreId(Long storeId);

    /**
     * 가게 사진을 조회하는 로직
     * @param storeId 조회할 매장의 id
     * @return 가게 사진
     */
    List<StoreImage> findStoreImages(Long storeId);

    //---------------------
    //주문금액 별 배달팁 조회
    List<DeliveryTipByAmount> findDeliveryTipByAmountByDeliveryId(Long deliveryId);

    //지역별 추가 배달팁 조회
    List<DeliveryTipByArea> findDeliveryTipByAreaByDeliveryId(Long deliveryId);
    //---------------------

    Integer findDeliveryTip(Long storeId, Integer totalAmount);
}
