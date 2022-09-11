package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.dto.PointDto;
import project.delivery.dto.PointHistoryDto;
import project.delivery.dto.PointHistorySearch;

import java.util.List;

@Transactional(readOnly = true)
public interface PointService {

    /**
     * 상품권을 조회하여 포인트를 충전하는 로직
     * @param memberId 회원 PK
     * @param voucherCode 12자리 상품권 번호
     * @return 충전한 금액
     * @exception project.delivery.exception.NoSuchException 상품권 번호가 존재하지 않는 경우, 포인트 정보를 찾을 수 없는 경우
     * @exception project.delivery.exception.DuplicateException 이미 등록처리된 상품권 번호인 경우
     */
    @Transactional
    Integer voucherRegistration(Long memberId, String voucherCode);

    /**
     * 포인트 정보를 조회하는 로직
     * @param memberId 회원 PK
     * @return PointDto
     * @exception project.delivery.exception.NoSuchException 포인트 정보를 찾을 수 없는 경우
     */
    PointDto findPointByMemberId(Long memberId);

    /**
     * 포인트 내역을 조회하는 로직
     * @param pointId 포인트 PK
     * @param search 조회조건(타입, 기간)
     * @return PointHistoryDto 리스트
     */
    List<PointHistoryDto> findPointHistoryByPointId(Long pointId, PointHistorySearch search);

    /**
     * 포인트 전체 잔액을 조회하는 로직
     * @param memberId 회원 PK
     * @return 포인트 전체 잔액
     */
    Integer findTotalPoint(Long memberId);
}
