package project.delivery.service.query;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.dto.LoginMember;

@Transactional(readOnly = true)
public interface MemberQueryService {

    LoginMember findLoginMember(Long memberId);
}
