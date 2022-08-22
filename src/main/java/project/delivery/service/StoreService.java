package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Category;
import project.delivery.dto.StoreDto;
import project.delivery.dto.create.CreateStoreDto;

import java.util.List;

@Transactional(readOnly = true)
public interface StoreService {

    @Transactional
    Long createNewStore(CreateStoreDto createStoreDto);

    List<StoreDto> searchStores(Category category);

    StoreDto detailStore(Long storeId);
}
