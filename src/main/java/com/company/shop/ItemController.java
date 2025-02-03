package com.company.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping("/list")
    String list(Model model){
        model.addAttribute("name","비싼바지");

        return "list.html";
    }
}
