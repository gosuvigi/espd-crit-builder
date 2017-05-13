package com.vigi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by vigi on 5/13/2017.
 */
@Controller
@RequestMapping("/")
class HomeController {

    @GetMapping
    public String index() {
        return "index";
    }
}
