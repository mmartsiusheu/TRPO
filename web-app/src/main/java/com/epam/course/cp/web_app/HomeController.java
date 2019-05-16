package com.epam.course.cp.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This controller redirect to home uri
 */
@Controller
public class HomeController {

    @GetMapping(value = "/")
    public final String home() {
        return "redirect:categories";
    }
}
