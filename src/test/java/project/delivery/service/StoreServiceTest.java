package project.delivery.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Category;
import project.delivery.domain.Store;
import project.delivery.domain.UploadFile;
import project.delivery.exception.DuplicateException;
import project.delivery.repository.StoreRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StoreServiceTest {

    @Autowired StoreService storeService;
    @Autowired StoreRepository storeRepository;

    @Test
    @DisplayName("가게등록")
    void createNewStore() {
        Store store = new Store("test Store", Category.CHICKEN, new UploadFile(null, null), "0212345678", "introduction", "test",
                "10:00", "연중무휴", "전지역", "1,000원", "test", "address",
                "123-45-67890", "announcement");

        Long storeId = storeService.createNewStore(store);

        Optional<Store> findStore = storeRepository.findById(storeId);
        assertThat(findStore).isPresent();
    }

    @Test
    @DisplayName("가게등록-사업자번호 중복")
    void createNewStore_businessNumber() {
        Store store = createStore();
        Store newStore = new Store("test Store", Category.CHICKEN, new UploadFile(null, null), "0212345678", "introduction", "test",
                "10:00", "연중무휴", "전지역", "1,000원", "test", "address",
                "123-45-67890", "announcement");

        assertThrows(DuplicateException.class, () -> {
            storeService.createNewStore(newStore);
        });
    }

    private Store createStore() {
        Store store = new Store("test Store", Category.CHICKEN, null, "0212345678", "introduction", "test",
                "10:00", "연중무휴", "전지역", "1,000원", "test", "address",
                "123-45-67890", "announcement");
        return storeRepository.save(store);
    }
}