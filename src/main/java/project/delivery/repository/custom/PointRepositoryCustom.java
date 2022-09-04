package project.delivery.repository.custom;

import project.delivery.domain.PointType;
import project.delivery.dto.PointDto;
import project.delivery.dto.PointHistoryDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PointRepositoryCustom {

    /**
     *
     * @param memberId 회원 PK
     * @return id, totalPoint, removePoint, balance
     */
    PointDto findPointByMemberId(Long memberId);

    /**
     *
     * @param pointId 포인트 PK
     * @param type PointType
     * @param period 조회 기간
     * @return content, pointValue, type, createdDate
     */
    List<PointHistoryDto> findPointHistoryByPointId(Long pointId, PointType type, LocalDateTime period);
}
