package com.humber.final_project.controllers;

import com.humber.final_project.models.ClaimForm;
import com.humber.final_project.models.LoginForm;
import com.humber.final_project.models.UserRegistrationForm;
import com.humber.final_project.models.ProductRegistrationForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

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
    public String registerUser(UserRegistrationForm userRegistrationForm, Model model) {
        if (isRegistrationSuccessful(userRegistrationForm)) {
            model.addAttribute("message", "Registration Successful");
            return "redirect:/registrationSuccess";
        } else {
            model.addAttribute("message", "Please fill out right details");
            return "redirect:/registrationFailure";
        }
    }

    // Add your registration logic here
    private boolean isRegistrationSuccessful(UserRegistrationForm userRegistrationForm) {
        // Implement your logic to determine if the registration is successful.
        // For simplicity, check if all fields are filled.
        return userRegistrationForm != null &&
                userRegistrationForm.getUserId() != null &&
                userRegistrationForm.getUsername() != null &&
                userRegistrationForm.getPassword() != null &&
                userRegistrationForm.getEmail() != null &&
                userRegistrationForm.getPhoneNumber() != null &&
                userRegistrationForm.getName() != null;
    }

    @PostMapping("/login")
    public String loginUser(LoginForm loginForm, Model model) {
        // Implement your login logic here.
        // For simplicity, let's assume a successful login if all fields are filled.
        if (isLoginSuccessful(loginForm)) {
            return "redirect:/homePage";
        } else {
            return "redirect:/loginFailure";
        }
    }

    // Add your login logic here
    private boolean isLoginSuccessful(LoginForm loginForm) {
        // Implement your logic to determine if the login is successful.
        // For simplicity, check if all fields are filled.
        return loginForm != null &&
                loginForm.getUserId() != null &&
                loginForm.getUsername() != null &&
                loginForm.getPassword() != null;
    }

    @GetMapping("/loginFailure")
    public String showLoginFailurePage() {
        return "loginFailure";
    }

    @GetMapping("/homePage")
    public String showHomePage() {
        return "homePage";
    }

    @GetMapping("/registerProduct")
    public String showRegisterProductPage() {
        return "registerProduct";
    }

    @PostMapping("/registerProduct")
    public String registerProduct(ProductRegistrationForm productRegistrationForm, Model model) {
        // Implement your product registration logic here.
        // For simplicity, let's assume a successful registration if all fields are filled.
        if (isProductRegistrationSuccessful(productRegistrationForm)) {
            model.addAttribute("message", "Product Registration Successful");
            return "redirect:/productRegisterSuccess";
        } else {
            model.addAttribute("message", "Please fill out correct details to get your product registered");
            return "redirect:/productRegisterFailure";
        }
    }

    // Add your product registration logic here
    private boolean isProductRegistrationSuccessful(ProductRegistrationForm productRegistrationForm) {
        // Implement your logic to determine if the product registration is successful.
        // For simplicity, check if all fields are filled.
        return productRegistrationForm != null &&
                productRegistrationForm.getRegistrationId() != null &&
                productRegistrationForm.getProductName() != null &&
                productRegistrationForm.getSerialNo() != null &&
                productRegistrationForm.getPurchaseDate() != null;
    }

    @GetMapping("/productRegisterSuccess")
    public String showProductRegisterSuccessPage() {
        return "productRegisterSuccess";
    }

    @GetMapping("/productRegisterFailure")
    public String showProductRegisterFailurePage() {
        return "productRegisterFailure";
    }



    @GetMapping("/claimProduct")
    public String showClaimProductPage() {
        return "claimProduct";
    }

    @PostMapping("/claimProduct")
    public String claimProduct(ClaimForm claimForm, Model model) {
        // Implement your claim logic here.
        // For simplicity, let's assume a successful claim if all fields are filled.
        if (isClaimSuccessful(claimForm)) {
            model.addAttribute("message", "Claim for this Product is Successful");
            return "redirect:/claimSuccess";
        } else {
            model.addAttribute("message", "Sorry, your Claim for this Product gets Fail");
            return "redirect:/claimFailure";
        }
    }

    // Add your claim logic here
    private boolean isClaimSuccessful(ClaimForm claimForm) {
        // Implement your logic to determine if the claim is successful.
        // For simplicity, check if all fields are filled.
        return claimForm != null &&
                claimForm.getClaimId() != null &&
                claimForm.getRegistrationId() != null &&
                claimForm.getClaimDate() != null &&
                claimForm.getDescription() != null;
    }

    @GetMapping("/claimSuccess")
    public String showClaimSuccessPage() {
        return "claimSuccess";
    }

    @GetMapping("/claimFailure")
    public String showClaimFailurePage() {
        return "claimFailure";
    }

    @GetMapping("/adminLogin")
    public String showAdminLoginPage() {
        return "adminLogin";
    }

    @GetMapping("/addProduct")
    public String showAddProductPage() {
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(ProductRegistrationForm productRegistrationForm, Model model) {
        // Implement your product addition logic here.
        // For simplicity, let's assume a successful addition if all fields are filled.
        if (isProductRegistrationSuccessful(productRegistrationForm)) {
            // Product added successfully, redirect to success page
            return "redirect:/addProductSuccess";
        } else {
            // Product addition failed, redirect to failure page
            return "redirect:/addProductFailure";
        }
    }


}
