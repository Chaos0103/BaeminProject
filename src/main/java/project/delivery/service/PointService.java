package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.PointType;
import project.delivery.dto.PointDto;
import project.delivery.dto.PointHistoryDto;
import project.delivery.dto.create.CreatePointHistoryDto;

import java.util.List;

@Transactional(readOnly = true)
public interface PointService {

    @Transactional
    void createPointHistory(Long memberId, CreatePointHistoryDto createPointHistory);

    PointDto getPointByMember(Long memberId);

    List<PointHistoryDto> searchPoint(Long pointId, PointType type, int month);
}
