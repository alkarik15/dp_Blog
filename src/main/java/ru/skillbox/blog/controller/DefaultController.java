package ru.skillbox.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Controller
public class DefaultController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
