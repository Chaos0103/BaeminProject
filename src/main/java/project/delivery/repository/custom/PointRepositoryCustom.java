package project.delivery.repository.custom;

import project.delivery.domain.PointHistory;
import project.delivery.domain.PointType;

import java.time.LocalDateTime;
import java.util.List;

public interface PointRepositoryCustom {
    List<PointHistory> findHistory(Long pointId, PointType type, LocalDateTime date);
}
