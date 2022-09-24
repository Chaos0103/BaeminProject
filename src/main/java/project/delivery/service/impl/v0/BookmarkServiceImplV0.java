package project.delivery.service.impl.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.domain.member.Bookmark;
import project.delivery.domain.member.Member;
import project.delivery.domain.store.Store;
import project.delivery.dto.BookmarkDto;
import project.delivery.exception.NoSuchException;
import project.delivery.repository.BookmarkRepository;
import project.delivery.repository.MemberRepository;
import project.delivery.repository.StoreRepository;
import project.delivery.service.BookmarkService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImplV0 implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long addBookmark(Long memberId, Long storeId) {
        Member findMember = memberRepository.findById(memberId).orElse(null);
        if (findMember == null) {
            throw new NoSuchException("등록되지 않은 회원입니다.");
        }
        Store findStore = storeRepository.findById(storeId).orElse(null);
        if (findStore == null) {
            throw new NoSuchException("등록된 가게가 없습니다.");
        }
        Bookmark bookmark = new Bookmark(findMember, findStore);
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
        return savedBookmark.getId();
    }

    @Override
    public void removeBookmark(Long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }
}
