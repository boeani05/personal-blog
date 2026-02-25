package com.bernhard.personal_blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/addArticle")
    public String addArticle() {
        return "admin/addArticle";
    }

    @GetMapping("/editArticle")
    public String editArticle() {
        return "admin/editArticle";
    }
}
