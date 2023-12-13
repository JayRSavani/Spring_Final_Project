package com.humber.final_project.controllers;

import com.humber.final_project.Services.UserService;
import com.humber.final_project.models.ProductRegistration;
import com.humber.final_project.models.Products;
import com.humber.final_project.models.Users;
import com.humber.final_project.repositories.ProductRegistrationRepository;
import com.humber.final_project.repositories.ProductRepository;
import com.humber.final_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    UserRepository userRepository;
    ProductRepository productRepository;
    ProductRegistrationRepository productRegistrationRepository;

    UserService userService;

    @Autowired
    public MainController(UserRepository userRepository, ProductRepository productRepository,
                          ProductRegistrationRepository productRegistrationRepository,
                          UserService userService) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productRegistrationRepository = productRegistrationRepository;
        this.userService = userService;

    }

    @GetMapping("/")
    public String showMainPage() {
        return "mainPage";
    }

    @GetMapping("/403")
    public String show403Page() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities());
        return "403";
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
System.out.println(user1);
        if (user1 != null && user1.getPassword().equals(user.getPassword())) {
            model.addAttribute("user", user1);

            if (user1.getRole().equals(Users.Role.ADMIN)) {
                return "/adminLogin";
            } else {
                return "/HomePage";
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

//    @GetMapping("/registerProduct")
//    public String showRegisterProductPage() {
//        return "registerProduct";
//    }

    @GetMapping("/ProductRegisterSuccess")
    public String showProductRegistrationSuccessPage() {
        return "ProductRegisterSuccess";
    }

    @GetMapping("/ProductRegisterFailure")
    public String showProductRegistrationFailurePage() {
        return "ProductRegisterFailure";
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



    @GetMapping("/registerProduct")
    public String showRegisterProductPage(Model model) {
        List<Products> products = (List<Products>)this.productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("product", new Products());

        return "registerProduct";
    }

    @PostMapping("/registerProduct")
    public String registerProduct(@ModelAttribute("product") Products product,
                                  @RequestParam("serialNo") String serialNo,
                                  @RequestParam("purchaseDate") String purchaseDate,
                                  Model model) {

        Users user = userService.getCurrentLoggedInUser();
        ProductRegistration productRegistration = new ProductRegistration();
        productRegistration.setSerialNumber(serialNo);
        productRegistration.setPurchaseDate(parseDate(purchaseDate)); // Implement this method to parse the date
        productRegistration.setUser(user);
        productRegistration.setProduct(productRepository.findById(product.getId()).get());

        productRegistrationRepository.save(productRegistration);

        return "redirect:/ProductRegisterSuccess";
    }

    private Date parseDate(String dateString) {

        return new Date();
    }














}
