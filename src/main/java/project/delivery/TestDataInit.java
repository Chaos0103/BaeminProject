package project.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.delivery.domain.*;
import project.delivery.repository.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final NotificationRepository notificationRepository;
    private final PayAccountRepository payAccountRepository;
    private final PayCardRepository payCardRepository;
    private final CouponRepository couponRepository;

    @PostConstruct
    private void init() {
        Address address = new Address("06544", "서울시 서초구 신반포로 270", "101-101");
        Member member = new Member("lyt1228@naver.com", "pw12345678@", "임우택", "19980103", "01088888888", "chaos", address);

        Pay pay = new Pay(member, "123456");
        pay.addMoney(1000000);

        Point point = new Point(member);
        PointHistory pointHistory = new PointHistory(1000, "BBQ 간석중앙점", PointType.USE);
        pointHistory.addPoint(point);
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

        Notification notification1 = new Notification("BBQ 간석중앙점", "test", NotificationType.REVIEW);
        notification1.addMember(member);
        Notification notification2 = new Notification("BBQ 간석중앙점", "test", NotificationType.COMPLETE);
        notification2.addMember(member);
        Notification notification3 = new Notification("BBQ 간석중앙점", "test", NotificationType.DELIVERY);
        notification3.addMember(member);
        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
        notificationRepository.save(notification3);

        PayAccount payAccount = new PayAccount(pay, Bank.KAKAOBANK, "3333-00-1234567");
        payAccountRepository.save(payAccount);

        PayCard payCard = new PayCard(Card.KAKAO, "1234123412341234", "1225", "000", "12");
        payCard.addPay(pay);
        payCardRepository.save(payCard);

        Coupon coupon1 = new Coupon(member, "2222222222222222", "BBQ 간석중앙점", 20000, 1000, LocalDateTime.now().plusMonths(1));
        coupon1.changeStatus(CouponStatus.EXPIRE);
        Coupon coupon2 = new Coupon(member, "3333333333333333", "BBQ 간석중앙점", 20000, 1000, LocalDateTime.now().plusHours(12));
        coupon2.changeStatus(CouponStatus.USE);
        Coupon coupon3 = new Coupon(member, "4444444444444444", "BBQ 간석중앙점", 20000, 1000, LocalDateTime.now().plusHours(12));
        coupon3.changeStatus(CouponStatus.UNUSE);
        couponRepository.save(coupon1);
        couponRepository.save(coupon2);
        couponRepository.save(coupon3);
    }
}
