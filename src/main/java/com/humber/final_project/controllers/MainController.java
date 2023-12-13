package com.humber.final_project.controllers;
import com.humber.final_project.models.Claim.ClaimStatus;

import com.humber.final_project.models.Claim;
import com.humber.final_project.models.ProductRegistration;
import com.humber.final_project.models.Products;
import com.humber.final_project.models.Users;
import com.humber.final_project.repositories.ClaimRepository;
import com.humber.final_project.repositories.ProductRegistrationRepository;
import com.humber.final_project.repositories.ProductRepository;
import com.humber.final_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    ClaimRepository claimRepository;

    @Autowired
    public MainController(UserRepository userRepository, ProductRepository productRepository,
                          ProductRegistrationRepository productRegistrationRepository,
                          ClaimRepository claimRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productRegistrationRepository = productRegistrationRepository;
        this.claimRepository = claimRepository;
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

    @GetMapping("/ProductRegisterSuccess")
    public String showProductRegistrationSuccessPage() {
        return "ProductRegisterSuccess";
    }

    @GetMapping("/ProductRegisterFailure")
    public String showProductRegistrationFailurePage() {
        return "ProductRegisterFailure";
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

        productRepository.save(product);

        return "redirect:/addProductSuccess";
    }

    private List<String> getProductNames() {
        List<String> productNames = new ArrayList<>();
        Iterable<Products> products = productRepository.findAll();
        for (Products product : products) {
            productNames.add(product.getName());
        }
        return productNames;
    }


    @GetMapping("/registerProduct")
    public String showRegisterProductPage(Model model) {
        List<String> productNames = getProductNames();
        model.addAttribute("productNames", productNames);
        model.addAttribute("product", new Products());

        return "registerProduct";
    }

    @PostMapping("/registerProduct")
    public String registerProduct(@ModelAttribute("product") Products product,
                                  @RequestParam("serialNo") String serialNo,
                                  @RequestParam("purchaseDate") String purchaseDate,
                                  Model model) {
        Users user = fetchLoggedInUser();

        ProductRegistration productRegistration = new ProductRegistration();
        productRegistration.setSerialNumber(serialNo);
        productRegistration.setPurchaseDate(parseDate(purchaseDate));
        productRegistration.setUser(user);

        productRegistrationRepository.save(productRegistration);

        return "redirect:/ProductRegisterSuccess";
    }

    private Users fetchLoggedInUser() {

        return userRepository.findByUsername("admin");
    }

    private Date parseDate(String dateString) {
        return new Date();
    }


    @GetMapping("/claimProduct")
    public String showClaimProductPage(Model model) {
        List<ProductRegistration> registrations = (List<ProductRegistration>) productRegistrationRepository.findAll();

        model.addAttribute("registrations", registrations);

        return "claimProduct";
    }

    @PostMapping("/claimProduct")
    public String claimProduct(@RequestParam("registrationId") int registrationId,
                               @RequestParam("claimDate") String claimDate,
                               @RequestParam("description") String description) {

        // Check if the product registration exists
        ProductRegistration productRegistration = productRegistrationRepository.findById(registrationId).orElse(null);

        if (productRegistration != null) {
            Claim claim = new Claim();
            claim.setClaimDate(parseDate(claimDate));
            claim.setClaimDescription(description);
            claim.setClaimStatus(Claim.ClaimStatus.PENDING);
            claim.setProductRegistration(productRegistration);

            // Save the updated ProductRegistration
            productRegistrationRepository.save(productRegistration);

            // Save the new Claim
            claimRepository.save(claim);

            return "redirect:/ClaimSuccess";
        } else {
            // Redirect to claim failure if registration ID does not exist
            return "redirect:/ClaimFailure";
        }
    }



















}
