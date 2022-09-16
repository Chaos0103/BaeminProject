package project.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.delivery.domain.member.Member;
import project.delivery.login.Login;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GlobalInformation globalInfo;

    /**
     * @URL: localhost:8080
     */
    @GetMapping
    public String mainHome(@Login Member loginMember, Model model) {

        if (loginMember == null) {
            return "home";
        }

        globalInfo.headerInfo(loginMember, model);
        return "loginHome";
    }
}
