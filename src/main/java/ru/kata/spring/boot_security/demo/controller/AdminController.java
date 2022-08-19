package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    @Autowired
    public AdminController(UserService userService) {this.userService = userService;}

    @GetMapping()
    public String getUsers(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }

    @GetMapping(value = "/{id}")
    public String getUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user";
    }

    @GetMapping("/create")
    public String createNew(Model model, User user) {
        model.addAttribute("listRoles", userService.listRoles());
        return "create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(required = false, value = "roles") int[] roles) {
        user.setRoles(userService.getRolesByIdArr(roles));
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("listRoles", userService.listRoles());
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam(required = false, value = "roles") int[] roles,
                             @PathVariable("id") int id) {
        user.setRoles(userService.getRolesByIdArr(roles));
        userService.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}

