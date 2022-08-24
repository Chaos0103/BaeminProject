package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Member;
import project.delivery.domain.Point;
import project.delivery.domain.PointHistory;
import project.delivery.domain.PointType;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.PointRepository;
import project.delivery.service.PointService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImplV0 implements PointService {

    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    @Override
    public void createPointHistory(Long memberId, PointHistory pointHistory) {
        Member findMember = getMember(memberId);
        Point point = findMember.getPoint();
        pointHistory.addPoint(point);
    }

    @Override
    public Point getPointByMember(Long memberId) {
        return pointRepository.findByMemberId(memberId).orElseThrow(() -> {
            throw new NoSuchException("데이터가 존재하지 않습니다");
        });
    }

    @Override
    public List<PointHistory> searchPoint(Long pointId, PointType type, int month) {
        LocalDateTime date = LocalDateTime.now().minusMonths(month);
        return pointRepository.findHistory(pointId, type, date);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchException("등록되지 않은 회원입니다");
        });
    }
}
