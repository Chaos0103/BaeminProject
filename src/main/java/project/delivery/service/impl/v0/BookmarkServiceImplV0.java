package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.member.Bookmark;
import project.delivery.domain.member.Member;
import project.delivery.domain.store.Store;
import project.delivery.dto.BookmarkDto;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.BookmarkRepository;
import project.delivery.repository.StoreRepository;
import project.delivery.service.BookmarkService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImplV0 implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final StoreRepository storeRepository;

    @Override
    public Long addBookmark(Member loginMember, Long storeId) {
        Store findStore = storeRepository.findById(storeId).orElse(null);
        if (findStore == null) {
            throw new NoSuchException("등록된 가게가 없습니다.");
        }
        Bookmark bookmark = new Bookmark(loginMember, findStore);
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
        return savedBookmark.getId();
    }

    @Override
    public void removeBookmark(Long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }
}
