package project.delivery.service.query;

import org.springframework.transaction.annotation.Transactional;
import project.delivery.dto.BookmarkDto;

import java.util.List;

@Transactional(readOnly = true)
public interface BookmarkQueryService {

    List<BookmarkDto> findBookmarks(Long memberId);
}
