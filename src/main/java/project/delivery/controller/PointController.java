package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.delivery.domain.Member;
import project.delivery.domain.PointType;
import project.delivery.dto.PointDto;
import project.delivery.dto.PointHistoryDto;
import project.delivery.service.PointService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {

    private final PointService pointService;

    @GetMapping
    public String pointHome(HttpSession session, Model model) {
        Member loginMember = getLoginMember(session);

        PointDto pointDto = pointService.getPointByMember(loginMember.getId());
        List<PointHistoryDto> pointHistoryDtos = pointService.searchPoint(pointDto.getId(), PointType.USE, 1);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("point", pointDto);
        model.addAttribute("pointHistories", pointHistoryDtos);
        return "member/point";
    }

    private Member getLoginMember(HttpSession session) {
        return (Member) session.getAttribute("loginMember");
    }
}
