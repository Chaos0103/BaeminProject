package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Member;
import project.delivery.domain.Point;
import project.delivery.domain.PointHistory;
import project.delivery.domain.PointType;
import project.delivery.dto.PointHistorySearch;

import java.util.List;

@Transactional(readOnly = true)
public interface PointService {

    @Transactional
    Integer addVoucher(Long memberId, String voucherCode);

    Point getPointByMember(Long memberId);

    List<PointHistory> findPointHistory(Long pointId, PointHistorySearch search);

    Integer findTotalPoint(Long memberId);
}
