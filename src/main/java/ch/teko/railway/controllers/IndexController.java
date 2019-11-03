package ch.teko.railway.controllers;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Index controller entrypoint of the web application
 */
@Controller
@Getter
public class IndexController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

}
