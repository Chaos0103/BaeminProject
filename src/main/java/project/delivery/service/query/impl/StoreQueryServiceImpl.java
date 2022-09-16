package project.delivery.service.query.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.store.*;
import project.delivery.repository.*;
import project.delivery.service.query.StoreQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreQueryServiceImpl implements StoreQueryService {

    private final StoreRepository storeRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final StoreImageRepository storeImageRepository;
    private final DeliveryTipByAmountRepository deliveryTipByAmountRepository;
    private final DeliveryTipByAreaRepository deliveryTipByAreaRepository;

    @Override
    public List<Store> findStores(Category category) {
        return storeRepository.findStores(category);
    }

    @Override
    public Store findStoreDetail(Long storeId) {
        return storeRepository.findStoreDetail(storeId);
    }

    @Override
    public List<MenuCategory> findMenuCategoryByStoreId(Long storeId) {
        return menuCategoryRepository.findMenuCategoryByStoreId(storeId);
    }

    @Override
    public List<StoreImage> findStoreImages(Long storeId) {
        return storeImageRepository.findStoreImages(storeId);
    }

    @Override
    public List<DeliveryTipByAmount> findDeliveryTipByAmountByDeliveryId(Long deliveryId) {
        return deliveryTipByAmountRepository.findAllByDeliveryId(deliveryId);
    }

    @Override
    public List<DeliveryTipByArea> findDeliveryTipByAreaByDeliveryId(Long deliveryId) {
        return deliveryTipByAreaRepository.findAllByDeliveryId(deliveryId);
    }

    @Override
    public Integer findDeliveryTip(Long storeId, Integer totalAmount) {
        return storeRepository.findDeliveryTip(storeId, totalAmount);
    }
}
