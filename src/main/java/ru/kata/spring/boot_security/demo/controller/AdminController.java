package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserServiceImpl userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getListUsers(Principal principal, Model model) {
        User user = userService.findByName(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("add")
    public String createAddUserForm(User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", userService.getAllRoles());
        return "/adduser";
    }

    @PostMapping("add")
    public String addUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delUser(id);
        return "redirect:/admin";
    }

    @GetMapping("update{id}")
    public String createUpdUserForm(@PathVariable("id") long id, Model model) {
        User user = userService.getUser(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute(user);
        model.addAttribute("roles", userService.getAllRoles());
        return "/update";
    }

    @PostMapping("update")
    public String updateUser(User user) {
        userService.updUser(user);
        return "redirect:/admin";
    }


}
