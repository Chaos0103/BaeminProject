package project.delivery.repository.custom;

import project.delivery.dto.BookmarkDto;

import java.util.List;

public interface BookmarkRepositoryCustom {

    List<BookmarkDto> findBookmarksByMemberId(Long memberId);
}
