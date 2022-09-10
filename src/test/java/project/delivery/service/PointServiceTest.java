package project.delivery.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.*;
import project.delivery.domain.member.Member;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.PointRepository;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PointServiceTest {

    @Autowired PointService pointService;
    @Autowired PointRepository pointRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @Rollback(value = false)
    void createPointHistory() {
        Member member = createMember();
        Point point = createPoint(member);
        PointHistory pointHistory = new PointHistory(point, 10000, "test", PointType.SAVE);

        pointService.createPointHistory(member.getId(), pointHistory);
        em.flush();

        PointHistory findPointHistory = em.find(PointHistory.class, point.getPointHistories().get(0).getId());
        assertThat(findPointHistory).isEqualTo(point.getPointHistories().get(0));
    }

    private Member createMember() {
        Address address = new Address("12345", "mainAddress", "detailAddress");
        Member member = new Member("test@test.com", "test1!", "user", "20010101", "01011111111", "tester", address);
        return memberRepository.save(member);
    }

    private Point createPoint(Member member) {
        Point point = new Point(member);
        return pointRepository.save(point);
    }
}