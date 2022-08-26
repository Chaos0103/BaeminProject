package project.delivery.admin.voucher;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoucherData {

    @Id @GeneratedValue
    @Column(name = "voucher_data_id")
    private Long id;

    @Column(updatable = false, nullable = false, length = 12)
    private String voucherCode;

    private String voucherName;
    private int pointValue;
    private LocalDateTime expiredDate;
    private boolean used;

    public VoucherData(String voucherCode, String voucherName, int pointValue, LocalDateTime expiredDate) {
        this.voucherCode = voucherCode;
        this.voucherName = voucherName;
        this.pointValue = pointValue;
        this.expiredDate = expiredDate;
        this.used = false;
    }

    //==비즈니스 로직==//
    public void changeUsed() {
        this.used = true;
    }
}
