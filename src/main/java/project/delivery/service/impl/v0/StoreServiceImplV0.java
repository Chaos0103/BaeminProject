package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.*;
import project.delivery.exception.DuplicateException;
import project.delivery.repository.*;
import project.delivery.service.StoreService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreServiceImplV0 implements StoreService {

    private final StoreRepository storeRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final StoreImageRepository storeImageRepository;
    private final DeliveryTipByAmountRepository deliveryTipByAmountRepository;
    private final DeliveryTipByAreaRepository deliveryTipByAreaRepository;

    @Override
    public Long createNewStore(Store store) {
        duplicatedBusinessNumber(store.getBusinessNumber());

        Store savedStore = storeRepository.save(store);
        return savedStore.getId();
    }

    @Override
    public List<Store> findStoresByCategory(Category category) {
        return storeRepository.findAllByCategory(category);
    }

    @Override
    public Store findStoreById(Long storeId) {
        return storeRepository.findDetailByStoreId(storeId);
    }

    @Override
    public List<MenuCategory> findMenuCategoryByStoreId(Long storeId) {
        return menuCategoryRepository.findMenuCategoryByStoreId(storeId);
    }

    @Override
    public List<StoreImage> findStoreBannerImages(Long storeId) {
        return storeImageRepository.findBannerByStoreId(storeId);
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

    private void duplicatedBusinessNumber(String businessNumber) {
        Optional<Store> findStore = storeRepository.findByBusinessNumber(businessNumber);
        if (findStore.isPresent()) {
            throw new DuplicateException("이미 등록된 사업자 번호입니다");
        }
    }
}
