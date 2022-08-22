package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Member;
import project.delivery.domain.Point;
import project.delivery.domain.PointHistory;
import project.delivery.domain.PointType;
import project.delivery.dto.PointDto;
import project.delivery.dto.PointHistoryDto;
import project.delivery.dto.create.CreatePointHistoryDto;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.PointRepository;
import project.delivery.service.PointService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointServiceImplV0 implements PointService {

    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    @Override
    public void createPointHistory(Long memberId, CreatePointHistoryDto createPointHistory) {
        Member findMember = getMember(memberId);
        Point point = findMember.getPoint();
        PointHistory pointHistory = new PointHistory(point, createPointHistory.getPointValue(), createPointHistory.getContent(), createPointHistory.getType());
    }

    @Override
    public PointDto getPointByMember(Long memberId) {
        Point findPoint = pointRepository.findByMemberId(memberId).orElseThrow(() -> {
            throw new NoSuchException("데이터가 존재하지 않습니다");
        });
        return new PointDto(findPoint);
    }

    @Override
    public List<PointHistoryDto> searchPoint(Long pointId, PointType type, int month) {
        LocalDateTime date = LocalDateTime.now().minusMonths(month);
        List<PointHistory> pointHistories = pointRepository.findHistory(pointId, type, date);
        return pointHistories.stream()
                .map(PointHistoryDto::new)
                .toList();
    }

    private Point getPoint(Long memberId) {
        return pointRepository.findByMemberId(memberId).orElseThrow(() -> {
            throw new NoSuchException("등록되지 않은 회원입니다");
        });
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchException("등록되지 않은 회원입니다");
        });
    }
}
