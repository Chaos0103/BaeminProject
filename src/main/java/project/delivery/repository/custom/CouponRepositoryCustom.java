package project.delivery.repository.custom;

import project.delivery.dto.CouponDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepositoryCustom {

    List<CouponDto> findCouponByMemberId(Long memberId, LocalDateTime period);
}
