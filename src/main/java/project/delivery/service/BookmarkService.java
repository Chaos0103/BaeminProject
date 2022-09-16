package project.delivery.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookmarkService {

    Long addBookmark(Long memberId, Long storeId);

    void removeBookmark(Long bookmarkId);
}
