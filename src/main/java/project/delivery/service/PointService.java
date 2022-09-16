package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PointService {

    /**
     * 상품권을 조회하여 포인트를 충전하는 로직
     * @param memberId 회원 PK
     * @param voucherCode 12자리 상품권 번호
     * @return 충전한 금액
     * @exception project.delivery.exception.NoSuchException 상품권 번호가 존재하지 않는 경우, 포인트 정보를 찾을 수 없는 경우
     * @exception project.delivery.exception.DuplicateException 이미 등록처리된 상품권 번호인 경우
     */
    Integer voucherRegistration(Long memberId, String voucherCode);
}
