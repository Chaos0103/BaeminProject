package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.store.*;
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
    public DeliveryInfo findDeliveryInfo(Long storeId) {
        return null;
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
