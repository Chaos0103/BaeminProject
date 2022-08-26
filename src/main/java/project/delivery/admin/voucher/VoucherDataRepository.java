package project.delivery.admin.voucher;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoucherDataRepository extends JpaRepository<VoucherData, Long> {
    Optional<VoucherData> findByVoucherCode(String voucherCode);
}
