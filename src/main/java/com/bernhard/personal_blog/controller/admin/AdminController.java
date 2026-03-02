package com.bernhard.personal_blog.controller.admin;

import com.bernhard.personal_blog.entity.Article;
import com.bernhard.personal_blog.repo.ArticleRepository;
import com.bernhard.personal_blog.service.ArticleService;
import com.bernhard.personal_blog.status.ArticleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {

        List<Article> articles = articleRepository.findAllByOrderByPublishedAtDesc();
        model.addAttribute("articles", articles);

        return "admin/dashboard";
    }

    @GetMapping("/addArticle")
    public String addArticle() {
        return "admin/addArticle";
    }

    @GetMapping("/editArticle/{id}")
    public String editArticle(
            @PathVariable Long id,
            Model model
    ) {
        Article articleToEdit = articleService.findById(id);
        model.addAttribute("articleToEdit", articleToEdit);
        return "admin/editArticle";
    }

    @PostMapping("/dashboard")
    public String publishArticle(
            @RequestParam String articleTitle,
            @RequestParam String articleContent
    ) {

        try {
            articleService.createAndPublish(articleTitle, articleContent);
        } catch (IllegalArgumentException e) {
            return "redirect:/admin/addArticle?error=" + e.getMessage();
        }

        return "redirect:/admin/dashboard";
    }
}
