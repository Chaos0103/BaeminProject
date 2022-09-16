package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.dto.BookmarkDto;
import project.delivery.dto.LoginMember;
import project.delivery.login.Login;
import project.delivery.service.*;
import project.delivery.service.query.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/bookmarks")
public class BookmarkController {

    private final GlobalInformation globalInfo;

    private final BookmarkService bookmarkService;
    private final BookmarkQueryService bookmarkQueryService;

    @GetMapping
    public String bookmarkHome(@Login LoginMember loginMember, Model model) {
        globalInfo.headerInfo(loginMember, model);
        globalInfo.topInfo(loginMember, model);

        List<BookmarkDto> bookmarks = bookmarkQueryService.findBookmarks(loginMember.getId());
        model.addAttribute("bookmarks", bookmarks);
        return "member/bookmark";
    }

    @PostMapping("/{bookmarkId}/delete")
    public String removeBookmark(@PathVariable Long bookmarkId) {
        bookmarkService.removeBookmark(bookmarkId);
        return "redirect:/bookmarks";
    }
}
