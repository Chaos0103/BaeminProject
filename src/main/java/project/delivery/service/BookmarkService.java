package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.domain.member.Member;
import project.delivery.dto.BookmarkDto;

import java.util.List;

@Transactional(readOnly = true)
public interface BookmarkService {

    @Transactional
    Long addBookmark(Member loginMember, Long storeId);

    @Transactional
    void removeBookmark(Long bookmarkId);

    List<BookmarkDto> findBookmarksByMemberId(Long memberId);
}
