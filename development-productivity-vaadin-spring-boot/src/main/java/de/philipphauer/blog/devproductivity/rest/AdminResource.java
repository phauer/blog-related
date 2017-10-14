package de.philipphauer.blog.devproductivity.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminResource {

    @GetMapping("/")
    public String redirectToUI(){
        return "redirect:/ui";
    }

    @GetMapping("favicon.ico")
    public String favicon(){
        return "forward:/VAADIN/themes/sqljunkie/favicon.ico";
    }

    @GetMapping("/customResource")
    public String customResource(){
        return "Hi!";
    }
}
