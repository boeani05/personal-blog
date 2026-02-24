package com.bernhard.personal_blog.controller.guest;

import com.bernhard.personal_blog.entity.Article;
import com.bernhard.personal_blog.repo.ArticleRepository;
import com.bernhard.personal_blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GuestController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("articles", articleService.findAll());
        return "home";
    }

    @GetMapping("/article/{id}")
    public String articles(
            @PathVariable Long id, Model model
    ) {
        Article article = articleService.findById(id);
        model.addAttribute("article", article);

        return "public/article";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout-success")
    public String logoutPage() {
        return "logout-success";
    }
}
