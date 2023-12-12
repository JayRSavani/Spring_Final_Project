package com.humber.final_project.controllers;

import com.humber.final_project.models.Users;
import com.humber.final_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    UserRepository userRepository;
    @Autowired
    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String showMainPage() {
        return "mainPage";
    }

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    @PostMapping("/register")
    public String signUp(Users user, Model model) {
        userRepository.save(user);
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public String login(Users user, Model model) {
        System.out.println(user);
        Users user1 = userRepository.findByUsername(user.getUsername());
        System.out.println(user1);
        if (user1 != null) {
            if (user1.getPassword().equals(user.getPassword())) {
                model.addAttribute("user", user1);
                return "redirect:/";
            }
        }
        return "login";
    }




}
