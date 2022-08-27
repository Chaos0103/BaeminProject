package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.Bookmark;
import project.delivery.domain.Member;

import java.util.List;

@Transactional(readOnly = true)
public interface BookmarkService {

    @Transactional
    Long addBookmark(Member loginMember, Long storeId);

    @Transactional
    void removeBookmark(Long bookmarkId);

    List<Bookmark> findByMember(Long memberId);
}
