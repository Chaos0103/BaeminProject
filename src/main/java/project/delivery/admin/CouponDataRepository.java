package project.delivery.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponDataRepository extends JpaRepository<CouponData, Long> {

    Optional<CouponData> findByCouponCode(String couponCode);
}
