package com.bernhard.personal_blog.controller.guest;

import com.bernhard.personal_blog.entity.Article;
import com.bernhard.personal_blog.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GuestController {

    private static final Logger logger = LoggerFactory.getLogger(GuestController.class);

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String home(Model model) {
        logger.debug("Loading homepage");
        model.addAttribute("articles", articleService.findAllPublished());
        return "home";
    }

    @GetMapping("/article/{id}")
    public String articles(
            @PathVariable Long id,
            Model model
    ) {
        logger.debug("Loading article with ID: {}", id);
        Article article = articleService.findById(id);
        model.addAttribute("article", article);

        return "public/article";
    }

    @GetMapping("/login")
    public String loginPage() {
        logger.debug("Loading Login-Page");
        return "login";
    }

    @GetMapping("/logout-success")
    public String logoutPage() {
        logger.debug("Loading Logout-Page");
        return "logout-success";
    }
}
