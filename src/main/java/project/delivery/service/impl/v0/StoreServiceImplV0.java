package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Category;
import project.delivery.domain.Store;
import project.delivery.dto.StoreDto;
import project.delivery.dto.create.CreateStoreDto;
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
    public Long createNewStore(CreateStoreDto createStoreDto) {
        duplicatedBusinessNumber(createStoreDto.getBusinessNumber());

        Store store = createStore(createStoreDto);
        Store savedStore = storeRepository.save(store);

        return savedStore.getId();
    }

    @Override
    public List<StoreDto> searchStores(Category category) {
        List<Store> findStores = storeRepository.findAllByCondition(category);
        return findStores.stream()
                .map(StoreDto::new)
                .toList();
    }

    private void duplicatedBusinessNumber(String businessNumber) {
        Optional<Store> findStore = storeRepository.findByBusinessNumber(businessNumber);
        if (findStore.isPresent()) {
            throw new DuplicateException("이미 등록된 사업자 번호입니다");
        }
    }

    private Store createStore(CreateStoreDto createStoreDto) {
        return new Store(createStoreDto.getStoreName(), createStoreDto.getCategory(), createStoreDto.getTel(), createStoreDto.getIntroduction(), createStoreDto.getIntroduction(),
                createStoreDto.getOpenTime(), createStoreDto.getHoliday(), createStoreDto.getDeliveryArea(), createStoreDto.getDeliveryTip(), createStoreDto.getRepresentativeName(),
                createStoreDto.getBusinessAddress(), createStoreDto.getBusinessNumber(), createStoreDto.getAnnouncement());
    }
}
