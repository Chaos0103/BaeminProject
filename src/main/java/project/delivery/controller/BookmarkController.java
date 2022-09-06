package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.domain.Bookmark;
import project.delivery.domain.Member;
import project.delivery.dto.BookmarkDto;
import project.delivery.dto.NotificationDto;
import project.delivery.login.Login;
import project.delivery.service.BookmarkService;
import project.delivery.service.NotificationService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final NotificationService notificationService;

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    @GetMapping
    public String bookmarkHome(@Login Member loginMember, Model model) {
        List<NotificationDto> notifications = notificationService.findNotificationByMemberId(loginMember.getId());
        List<BookmarkDto> bookmarks = bookmarkService.findBookmarksByMemberId(loginMember.getId());

        model.addAttribute("notifications", notifications);
        model.addAttribute("bookmarks", bookmarks);
        return "member/bookmark";
    }

    @PostMapping("/{bookmarkId}/delete")
    public String removeBookmark(@PathVariable Long bookmarkId) {
        bookmarkService.removeBookmark(bookmarkId);
        return "redirect:/bookmarks";
    }
}
