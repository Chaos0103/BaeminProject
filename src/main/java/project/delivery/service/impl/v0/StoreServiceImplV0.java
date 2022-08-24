package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Category;
import project.delivery.domain.Store;
import project.delivery.exception.DuplicateException;
import project.delivery.repository.StoreRepository;
import project.delivery.service.StoreService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreServiceImplV0 implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public Long createNewStore(Store store) {
        duplicatedBusinessNumber(store.getBusinessNumber());

        Store savedStore = storeRepository.save(store);
        return savedStore.getId();
    }

    @Override
    public List<Store> searchStores(Category category) {
        return storeRepository.findAllByCondition(category);
    }

    @Override
    public Store detailStore(Long storeId) {
        return storeRepository.findStore(storeId).orElse(null);
    }

    private void duplicatedBusinessNumber(String businessNumber) {
        Optional<Store> findStore = storeRepository.findByBusinessNumber(businessNumber);
        if (findStore.isPresent()) {
            throw new DuplicateException("이미 등록된 사업자 번호입니다");
        }
    }
}
