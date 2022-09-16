package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.member.Member;

@Transactional
public interface BookmarkService {

    Long addBookmark(Member loginMember, Long storeId);

    void removeBookmark(Long bookmarkId);
}
