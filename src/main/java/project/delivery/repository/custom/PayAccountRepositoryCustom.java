package project.delivery.repository.custom;

import project.delivery.dto.PayAccountDto;

import java.util.List;

public interface PayAccountRepositoryCustom {

    /**
     *
     * @param payId 페이 PK
     * @return id, accountNumber, bank, nickname
     */
    List<PayAccountDto> findPayAccountByPayId(Long payId);
}
