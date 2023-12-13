package com.humber.final_project.controllers;

import com.humber.final_project.Services.UserService;
import com.humber.final_project.models.Claim;
import com.humber.final_project.models.ProductRegistration;
import com.humber.final_project.models.Products;
import com.humber.final_project.models.Users;
import com.humber.final_project.repositories.ClaimRepository;
import com.humber.final_project.repositories.ProductRegistrationRepository;
import com.humber.final_project.repositories.ProductRepository;
import com.humber.final_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    UserRepository userRepository;
    ProductRepository productRepository;
    ProductRegistrationRepository productRegistrationRepository;
    ClaimRepository claimRepository;

    UserService userService;

    @Autowired
    public MainController(UserRepository userRepository, ProductRepository productRepository,
                          ProductRegistrationRepository productRegistrationRepository,
                          ClaimRepository claimRepository,
                          UserService userService) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productRegistrationRepository = productRegistrationRepository;
        this.userService = userService;
        this.claimRepository = claimRepository;
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
        if (productRepository.findByName(product.getName()) != null) {
            return "redirect:/addProductFailure";
        }
        productRepository.save(product);
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
        productRegistration.setPurchaseDate(parseDate(purchaseDate));
        productRegistration.setUser(user);
        productRegistration.setProduct(productRepository.findById(product.getId()).get());

        productRegistrationRepository.save(productRegistration);

        return "redirect:/ProductRegisterSuccess";
    }


    private Date parseDate(String dateString) {
        return new Date();
    }

    @GetMapping("/claimProduct")
    public String showClaimProductPage(Model model) {
        Users currentUser = userService.getCurrentLoggedInUser();

        List<ProductRegistration> registrations = productRegistrationRepository.findByUser(currentUser);

        model.addAttribute("registrations", registrations);

        return "claimProduct";
    }



    @PostMapping("/claimProduct")
    public String claimProduct(@RequestParam("registrationId") int registrationId,
                               @RequestParam("claimDate") String claimDate,
                               @RequestParam("description") String description) {

        ProductRegistration productRegistration = productRegistrationRepository.findById(registrationId).orElse(null);

        if (productRegistration != null) {
            Claim claim = new Claim();
            claim.setClaimDate(parseDate(claimDate));
            claim.setClaimDescription(description);
            claim.setClaimStatus(Claim.ClaimStatus.PENDING);
            claim.setProductRegistration(productRegistration);

            productRegistrationRepository.save(productRegistration);

            claimRepository.save(claim);

            return "redirect:/ClaimSuccess";
        } else {
            return "redirect:/ClaimFailure";
        }
    }

    @PostMapping("/saveClaimStatus/{claimId}")
    public String saveClaimStatus(@PathVariable Long claimId, @RequestParam("newStatus") String newStatus) {
        Claim claim = claimRepository.findById(Math.toIntExact(claimId)).orElse(null);

        if (claim != null) {
            claim.setClaimStatus(Claim.ClaimStatus.valueOf(newStatus));
            claimRepository.save(claim);
        }

        return "redirect:/claimData";
    }

    @GetMapping("/HomePage")
    public String showHomePage(Model model, Principal principal) {
        String username = principal.getName();

        List<Claim> userClaims = claimRepository.findByProductRegistrationUserUsername(username);

        model.addAttribute("userClaims", userClaims);

        return "HomePage";
    }


    @GetMapping("/adminLogin")
    public String showAdminLoginPage(Model model) {
        return "adminLogin";
    }

    @GetMapping("/productData")
    public String showProductDataPage(Model model) {
        List<Products> products = (List<Products>) productRepository.findAll();
        model.addAttribute("products", products);
        return "productData";
    }

    @GetMapping("/userData")
    public String showUserDataPage(Model model) {
        List<Users> users = (List<Users>) userRepository.findAll();
        model.addAttribute("users", users);
        return "userData";
    }

    @GetMapping("/productRegistrationData")
    public String showProductRegistrationDataPage(Model model) {
        List<ProductRegistration> registrations = (List<ProductRegistration>) productRegistrationRepository.findAll();
        model.addAttribute("registrations", registrations);
        return "productRegistrationData";
    }

    @GetMapping("/claimData")
    public String showClaimDataPage(Model model) {
        List<Claim> claims = (List<Claim>) claimRepository.findAll();
        model.addAttribute("claims", claims);
        return "claimData";
    }

    @PostMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable int productId) {
        Optional<Products> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Products product = productOptional.get();

            productRepository.delete(product);
        }

        return "redirect:/productData";
    }

}
