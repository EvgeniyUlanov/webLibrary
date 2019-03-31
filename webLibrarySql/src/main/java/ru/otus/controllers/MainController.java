package ru.otus.controllers;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/lib")
    public String getMainPage(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetails principal = (UserDetails) context.getAuthentication().getPrincipal();
        String username = principal.getUsername();
        model.addAttribute("userName", username);
        return "/mySinglePage";
    }

    @GetMapping("/")
    public String getWelcomePage() {
        return "/welcomePage";
    }
}
