package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Point;
import project.delivery.domain.PointHistory;
import project.delivery.domain.PointType;

import java.util.List;

@Transactional(readOnly = true)
public interface PointService {

    @Transactional
    void createPointHistory(Long memberId, PointHistory pointHistory);

    Point getPointByMember(Long memberId);

    List<PointHistory> searchPoint(Long pointId, PointType type, int month);
}
