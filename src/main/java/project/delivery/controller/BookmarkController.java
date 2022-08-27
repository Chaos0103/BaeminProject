package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.delivery.domain.Bookmark;
import project.delivery.domain.Member;
import project.delivery.domain.Notification;
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

    @ModelAttribute("notifications")
    public List<Notification> notifications(@Login Member loginMember) {
        return notificationService.findByMemberId(loginMember.getId());
    }

    @ModelAttribute("loginMember")
    public Member loginMember(@Login Member loginMember) {
        return loginMember;
    }

    @GetMapping
    public String bookmarkHome(@Login Member loginMember, Model model) {
        List<Bookmark> bookmarks = bookmarkService.findByMember(loginMember.getId());
        model.addAttribute("bookmarks", bookmarks);
        return "member/bookmark";
    }

    @PostMapping("/{bookmarkId}/delete")
    public String removeBookmark(@PathVariable Long bookmarkId) {
        bookmarkService.removeBookmark(bookmarkId);
        return "redirect:/bookmarks";
    }
}
