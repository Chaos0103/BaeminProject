package project.delivery.service.query.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.point.Point;
import project.delivery.dto.PointDto;
import project.delivery.dto.PointHistoryDto;
import project.delivery.dto.PointHistorySearch;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.PointRepository;
import project.delivery.service.query.PointQueryService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointQueryServiceImpl implements PointQueryService {

    private final PointRepository pointRepository;

    @Override
    public PointDto findPointByMemberId(Long memberId) {
        Point findPoint = getPoint(memberId);
        return new PointDto(findPoint);
    }

    @Override
    public List<PointHistoryDto> findPointHistoryByPointId(Long pointId, PointHistorySearch search) {
        return getPointHistoryDtos(pointId, search);
    }

    @Override
    public Integer findTotalPoint(Long memberId) {
        return pointRepository.findTotalPoint(memberId);
    }

    private Point getPoint(Long memberId) {
        Point findPoint = pointRepository.findByMemberId(memberId).orElse(null);
        if (findPoint == null) {
            throw new NoSuchException("포인트 내역을 찾을 수 없습니다");
        }
        return findPoint;
    }

    private List<PointHistoryDto> getPointHistoryDtos(Long pointId, PointHistorySearch search) {
        LocalDateTime period = LocalDateTime.now().minusMonths(search.getMonth());
        return pointRepository.findPointHistoryByPointId(pointId, search.getType(), period);
    }
}
