package com.cellar.wine.security.controller;

import com.cellar.wine.nav.Attributes;
import com.cellar.wine.nav.Paths;
import com.cellar.wine.security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Inject
    private UserService userService;

    @GetMapping("/userlist")
    public String getAllUsers(Model model) {
        model.addAttribute(Attributes.USERS, userService.findAll());
        return Paths.SECURITY_ADMIN_CONSOLE;
    }

}
