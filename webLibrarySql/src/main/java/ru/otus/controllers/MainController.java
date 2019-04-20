package ru.otus.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    public MainController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/lib")
    @PreAuthorize("isAuthenticated()")
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
            String encodedPassword = passwordEncoder.encode(password);
            userService.addNewUser(new User(userName, encodedPassword));
            message = "Registration success";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Registration error";
        }
        model.addAttribute("message", message);
        return "/welcomePage";
    }
}
