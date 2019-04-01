package ru.otus.controllers;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.User;
import ru.otus.services.UserService;

@Controller
public class MainController {

    private UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

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

    @PostMapping("/register")
    public String registerUser(
            @RequestParam("user_name") String userName,
            @RequestParam("password") String password,
            Model model) {
        String message;
        try {
            userService.addNewUser(new User(userName, password));
            message = "Registration success";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Registration error";
        }
        model.addAttribute("message", message);
        return "/welcomePage";
    }
}
