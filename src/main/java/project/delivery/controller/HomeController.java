package project.delivery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import project.delivery.domain.Member;
import project.delivery.login.Login;

@Slf4j
@Controller
public class HomeController {

    @GetMapping
    public String mainHome(@Login Member loginMember) {

        if (loginMember == null) {
            return "home";
        }
        return "loginHome";
    }
}
