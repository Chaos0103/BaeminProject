package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.Member;
import project.delivery.domain.Notification;
import project.delivery.dto.NotificationDto;
import project.delivery.dto.create.CreateNotificationDto;
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
    public Long createNotification(Long memberId, CreateNotificationDto createNotificationDto) {
        Member findMember = getMember(memberId);

        Notification notification = createNotificationData(createNotificationDto, findMember);
        Notification savedNotification = notificationRepository.save(notification);

        return savedNotification.getId();
    }

    @Override
    public List<NotificationDto> findByMemberId(Long memberId) {
        //3일 전 알림까지 조회 가능
        LocalDateTime date = LocalDateTime.now().minusDays(3);
        List<Notification> findNotifications = notificationRepository.findByMemberId(memberId, date);
        return findNotifications.stream()
                .map(NotificationDto::new)
                .toList();
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoSuchException("등록되지 않은 회원입니다");
        });
    }

    private static Notification createNotificationData(CreateNotificationDto createNotificationDto, Member findMember) {
        return new Notification(findMember, createNotificationDto.getStoreName(), createNotificationDto.getContent(), createNotificationDto.getType());
    }
}
