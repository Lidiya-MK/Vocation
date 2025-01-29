package com.ead.vocation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

    @GetMapping("/404")
    public String getMethodName() {
        return "404";
    }

}
