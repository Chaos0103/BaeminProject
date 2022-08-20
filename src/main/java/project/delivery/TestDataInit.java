package project.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.delivery.domain.*;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.PayRepository;
import project.delivery.repository.StoreRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final PayRepository payRepository;

    @PostConstruct
    private void init() {
        Address address = new Address("06544", "서울시 서초구 신반포로 270", "101-101");
        Member member = new Member("lyt1228@naver.com", "pw12345678@", "임우택", "19980103", "01088888888", "chaos", address);

        Pay pay = new Pay(member, "123456");
        pay.addMoney(1000000);
        memberRepository.save(member);

        Store store1 = new Store("BBQ 간석중앙점", Category.CHICKEN, new UploadFile("/file/bbq.png", "/file/bbq.png"), "0324297326", "안녕하세요!BBQ 간석중앙점을 찾아주셔서 감사합니다!",
                "비비큐(BBQ) 인천간석중앙점", "매일 - 오전 9:40 ~ 밤 12:00", "연중무휴", "남동구 전지역(일부지역 제외)", "2,900",
                "이선희", "인천광역시 남동구 간석동 303-11(간석동)", "819-08-01753", "간석중앙점 찜 & 리뷰 EVENT");
        new DeliveryInfo(store1, 15000, PaymentType.DIRECT, "44~59분 소요 예상", "0원 ~ 2,900원");
        storeRepository.save(store1);

        Store store2 = new Store("BBQ 인천시청점", Category.CHICKEN, new UploadFile("/file/bbq.png", "/file/bbq.png"), "05061155109", "** 믿고 찾아주시는 BBQ 인천시청점입니다. **",
                "비비큐인천시청점", "매일 - 오전 11:00 ~ 익일 새벽 2:00", "연중무휴", "구월동 주안동 간석동 관교동 만수동", "2,000원", "박진원",
                "인천광역시 남동구 구월동 1136 1층 105, 106호(구월동, 가온누리)", "186-17-01688", "BBQ 인청시청점 리.뷰.이.벤.트!");
        new DeliveryInfo(store2, 12000, PaymentType.DIRECT, "32~47분 소요 예상", "1,000원 ~ 3,000원");
        storeRepository.save(store2);

    }
}
