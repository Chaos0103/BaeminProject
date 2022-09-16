package project.delivery.repository.custom;

import project.delivery.domain.member.Bookmark;

import java.util.List;

public interface BookmarkRepositoryCustom {

    List<Bookmark> findBookmarks(Long memberId);
}
