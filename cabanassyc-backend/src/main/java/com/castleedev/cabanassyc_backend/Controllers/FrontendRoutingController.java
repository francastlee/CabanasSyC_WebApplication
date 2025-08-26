package com.castleedev.cabanassyc_backend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendRoutingController {

    @RequestMapping("/")
    public String redirectToHome() {
        return "forward:/home";
    }

    @RequestMapping({"/home", "/about", "/contact", "/tours", "/cabins", "/reservations"})
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}

