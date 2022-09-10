package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.member.Member;
import project.delivery.dto.NotificationDto;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.NotificationRepository;
import project.delivery.service.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImplV0 implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<NotificationDto> findNotificationByMemberId(Long memberId) {
        LocalDateTime period = LocalDateTime.now().minusDays(3);
        return notificationRepository.findNotificationByMemberId(memberId, period);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchException("등록되지 않은 회원입니다");
        });
    }
}
