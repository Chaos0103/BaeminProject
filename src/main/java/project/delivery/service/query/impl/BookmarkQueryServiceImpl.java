package project.delivery.service.query.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.delivery.dto.BookmarkDto;
import project.delivery.repository.BookmarkRepository;
import project.delivery.service.query.BookmarkQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkQueryServiceImpl implements BookmarkQueryService {

    private final BookmarkRepository bookmarkRepository;

    @Override
    public List<BookmarkDto> findBookmarksByMemberId(Long memberId) {
        return bookmarkRepository.findBookmarksByMemberId(memberId);
    }
}
