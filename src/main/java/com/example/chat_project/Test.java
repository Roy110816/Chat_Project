package com.example.chat_project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Test {

    @RequestMapping("/toin")
    public String toIndex(){
        return "index";
    }
}
