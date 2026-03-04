package com.bernhard.personal_blog.controller.admin;

import com.bernhard.personal_blog.entity.Article;
import com.bernhard.personal_blog.repo.ArticleRepository;
import com.bernhard.personal_blog.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

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

    @PostMapping("/editArticle")
    public String updateArticle(
            @ModelAttribute("article") Article article,
            RedirectAttributes redirectAttributes
    ) {
        logger.info("Updating article with ID: {}", article.getId());
        try {
            article.setUpdatedAt(LocalDateTime.now());
            articleService.updateArticle(article);
            logger.info("Successfully updated article: ID {}", article.getId());
            redirectAttributes.addFlashAttribute("success", "Successfully updated article");
        } catch (Exception e) {
            logger.error("Error while trying to update article: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error while trying to update article");
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/dashboard")
    public String publishArticle(
            @RequestParam(required = false) String articleTitle,
            @RequestParam(required = false) String articleContent,
            RedirectAttributes redirectAttributes
    ) {
        logger.info("Trying to publish new article");

        // Validierung
        if (articleTitle == null || articleTitle.trim().isEmpty()) {
            logger.warn("Can't create article: No title");
            redirectAttributes.addFlashAttribute("error", "Title must not be empty");
            return "redirect:/admin/addArticle";
        }

        if (articleContent == null || articleContent.trim().isEmpty()) {
            logger.warn("Can't create article: No content");
            redirectAttributes.addFlashAttribute("error", "Content must not be empty");
            return "redirect:/admin/addArticle";
        }

        try {
            Article article = articleService.createAndPublish(articleTitle, articleContent);
            logger.info("Successfully published article: ID {}", article.getId());
            redirectAttributes.addFlashAttribute("success", "Successfully published article");
        } catch (IllegalArgumentException e) {
            logger.error("Error while publishing article: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/addArticle";
        }

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/deleteArticle/{id}")
    public String deleteArticle(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        logger.info("Trying to delete article: {}", id);
        try {
            articleService.deleteArticleById(id);
            logger.info("Successfully deleted article: ID {}", id);
            redirectAttributes.addFlashAttribute("success", "Successfully deleted article");
        } catch (Exception e) {
            logger.error("Error while trying to delete article: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error while trying to delete article");
        }

        return "redirect:/admin/dashboard";
    }
}
