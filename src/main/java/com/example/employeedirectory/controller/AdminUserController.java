package com.example.employeedirectory.controller;

import com.example.employeedirectory.model.Role;
import com.example.employeedirectory.model.User;
import com.example.employeedirectory.service.UserService;
import jakarta.validation.Valid;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", Role.values());
        return "admin/users/list";
    }

    @PostMapping("/add")
    public String addUser(@NonNull @Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("roles", Role.values());
            return "admin/users/list";
        }
        try {
            userService.saveUserWithRawPassword(user);
        } catch (Exception e) {
            // e.g. duplicate username
            result.rejectValue("username", "error.user", "Username might already exist");
            model.addAttribute("users", userService.findAll());
            model.addAttribute("roles", Role.values());
            return "admin/users/list";
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@NonNull @PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/change-password")
    public String changePassword(@NonNull @RequestParam Long userId, @RequestParam String newPassword, 
                                 @RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match!");
            return "redirect:/admin/users";
        }
        if (newPassword.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "Password must be at least 6 characters long!");
            return "redirect:/admin/users";
        }
        userService.updatePassword(userId, newPassword);
        redirectAttributes.addFlashAttribute("success", "Password for user changed successfully!");
        return "redirect:/admin/users";
    }
}
