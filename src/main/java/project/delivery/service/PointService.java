package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.dto.PointDto;
import project.delivery.dto.PointHistoryDto;
import project.delivery.dto.PointHistorySearch;

import java.util.List;

@Transactional(readOnly = true)
public interface PointService {

    @Transactional
    Integer addVoucher(Long memberId, String voucherCode);

    PointDto findPointByMemberId(Long memberId);

    List<PointHistoryDto> findPointHistoryByPointId(Long pointId, PointHistorySearch search);

    Integer findTotalPoint(Long memberId);
}
