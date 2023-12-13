package com.humber.final_project.controllers;

import com.humber.final_project.models.Products;
import com.humber.final_project.models.Users;
import com.humber.final_project.repositories.ProductRepository;
import com.humber.final_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    UserRepository userRepository;
    ProductRepository productRepository;

    @Autowired
    public MainController(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
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
        Users user1 = userRepository.findByUsername(user.getUsername());

        if (user1 != null && user1.getPassword().equals(user.getPassword())) {
            model.addAttribute("user", user1);

            if ("Admin".equals(user1.getUsername()) && "12345678".equals(user1.getPassword())) {
                return "redirect:/adminLogin";
            } else {
                return "redirect:/HomePage";
            }
        } else {
            return "redirect:/loginFailure";
        }
    }


    @GetMapping("/loginFailure")
    public String showLoginFailurePage() {
        return "loginFailure";
    }

    @GetMapping("/HomePage")
    public String showHomePage() {
        return "HomePage";
    }

    @GetMapping("/registerProduct")
    public String showRegisterProductPage() {
        return "registerProduct";
    }

    @GetMapping("/ProductRegistrationSuccess")
    public String showProductRegistrationSuccessPage() {
        return "ProductRegistrationSuccess";
    }

    @GetMapping("/ProductRegistrationFailure")
    public String showProductRegistrationFailurePage() {
        return "ProductRegistrationFailure";
    }

    @GetMapping("/claimProduct")
    public String showClaimProductPage() {
        return "claimProduct";
    }

    @GetMapping("/ClaimSuccess")
    public String showClaimSuccessPage() {
        return "ClaimSuccess";
    }

    @GetMapping("/ClaimFailure")
    public String showClaimFailurePage() {
        return "ClaimFailure";
    }

    @GetMapping("/adminLogin")
    public String showAdminLoginPage() {
        return "adminLogin";
    }

    @GetMapping("/addProduct")
    public String showAddProductPage() {
        return "addProduct";
    }

    @GetMapping("/addProductSuccess")
    public String showAddProductSuccessPage() {
        return "addProductSuccess";
    }

    @GetMapping("/addProductFailure")
    public String showAddProductFailurePage() {
        return "addProductFailure";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("product") Products product, Model model) {
        // Check if the product name already exists
        if (productRepository.findByName(product.getName()) != null) {
            // Product name already exists, redirect to failure page
            return "redirect:/addProductFailure";
        }

        // Save the new product
        productRepository.save(product);

        // Product added successfully, redirect to success page
        return "redirect:/addProductSuccess";
    }










}
