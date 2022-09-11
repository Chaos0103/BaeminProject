package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.admin.voucher.VoucherData;
import project.delivery.admin.voucher.VoucherDataRepository;
import project.delivery.domain.Point;
import project.delivery.domain.PointHistory;
import project.delivery.domain.PointType;
import project.delivery.dto.PointDto;
import project.delivery.dto.PointHistoryDto;
import project.delivery.dto.PointHistorySearch;
import project.delivery.exception.DuplicateException;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.PointRepository;
import project.delivery.service.PointService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImplV0 implements PointService {

    private final PointRepository pointRepository;
    private final VoucherDataRepository voucherDataRepository;

    @Override
    public Integer voucherRegistration(Long memberId, String voucherCode) {
        VoucherData findVoucher = voucherDataRepository.findByVoucherCode(voucherCode).orElse(null);
        if (findVoucher == null) {
            throw new NoSuchException("상품권 번호를 다시 확인해주세요.");
        }
        if (findVoucher.isUsed()) {
            throw new DuplicateException("이미 등록된 상품권 번호입니다.");
        }
        findVoucher.changeUsed();
        Point findPoint = getPoint(memberId);
        if (findPoint == null) {
            throw new NoSuchException("포인트 내역을 찾을 수 없습니다");
        }
        PointHistory.createPointHistory(findPoint, findVoucher.getPointValue(), findVoucher.getVoucherName(), PointType.SAVE);
        return findVoucher.getPointValue();
    }

    @Override
    public PointDto findPointByMemberId(Long memberId) {
        Point findPoint = getPoint(memberId);
        if (findPoint == null) {
            throw new NoSuchException("포인트 내역을 찾을 수 없습니다");
        }
        return new PointDto(findPoint);
    }

    @Override
    public List<PointHistoryDto> findPointHistoryByPointId(Long pointId, PointHistorySearch search) {
        LocalDateTime period = LocalDateTime.now().minusMonths(search.getMonth());
        return pointRepository.findPointHistoryByPointId(pointId, search.getType(), period);
    }

    @Override
    public Integer findTotalPoint(Long memberId) {
        return pointRepository.findTotalPoint(memberId);
    }

    private Point getPoint(Long memberId) {
        return pointRepository.findByMemberId(memberId).orElse(null);
    }
}
