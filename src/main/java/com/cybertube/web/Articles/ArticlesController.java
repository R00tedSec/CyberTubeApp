package com.cybertube.web.Articles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cybertube.web.Users.User;
import com.cybertube.web.Users.UserService;
import com.cybertube.web.Comments.CommentService;

@Controller
public class ArticlesController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticlesRepository articlesRepository;

    @GetMapping("/articles")
    public String allArticles(final Model model) {
        model.addAttribute("articles", articlesService.findAllArticles());
        return "articles";
    }

    @GetMapping("/article/{articleID}")
    public String getArticle(HttpServletRequest request, final Model model, @PathVariable final long articleID) {
        User actualUser = null;
        Article articleToShow = articlesService.findArticleById(articleID);
        model.addAttribute("article", articleToShow);
        model.addAttribute("comment_list", articleToShow.getCommentaries());
        if (request.getUserPrincipal() != null) {
            actualUser = userService.getUserByName(request.getUserPrincipal().getName());
            model.addAttribute("check",
                    request.getUserPrincipal().getName().equalsIgnoreCase(articleToShow.getArticleAuthor()));
        } else {
            model.addAttribute("check", false);

        }

        if (actualUser != null) {
            model.addAttribute("checkLogged", true);
            if (articleToShow.getLikeArticleEntity().contains(actualUser)) {
                model.addAttribute("checkLike", true);
            } else {
                model.addAttribute("checkLike", false);
            }
        } else {
            model.addAttribute("checkLogged", false);
        }
        return "article";
    }

    @GetMapping("/postArticle")
    public String getPostArticle() {
        return "postArticle";
    }

    @PostMapping("/addArticle")
    public String addArticle(Authentication authentication, final Model model, @RequestParam final String title,
            @RequestParam final String description, @RequestParam final List<String> categories,
            @RequestParam final String content) {
        final Article newArticle = new Article(title, description, categories, content,
                userService.getUserByUsername(authentication.getName()));
        this.articlesService.addArticle(newArticle);
        return "redirect:/";
    }

    @RequestMapping(value = "/article/deleteArticle/{articleID}", method = { RequestMethod.GET })
    public String deleteArticle(HttpServletRequest request, @PathVariable long articleID) {
        if (request.isUserInRole("ROLE_ADMIN") || this.articlesService.findArticleById(articleID).getArticleAuthor()
                .equalsIgnoreCase(request.getUserPrincipal().getName())) {
            this.articlesService.removeArticle(articleID);
            return "delete_confirm";
        } else {
            return "error";
        }
    }

    @RequestMapping(value = "/article/updateArticle/{articleID}", method = { RequestMethod.GET })
    public String modifyForm(Model model, @PathVariable long articleID) {
        // Cogemos el vídeo a modificar
        model.addAttribute("article", this.articlesService.findArticleById(articleID));
        return "modify_article";
    }

    @PostMapping("/updateArticle/{articleID}")
    public String modifyArticle(HttpServletRequest request, Authentication authentication, Model model,
            @PathVariable final int articleID, @RequestParam final String title, @RequestParam final String description,
            @RequestParam List<String> categories, @RequestParam String content) {

        if (request.isUserInRole("ROLE_ADMIN") || this.articlesService.findArticleById(articleID).getArticleAuthor()
                .equalsIgnoreCase(request.getUserPrincipal().getName())) {
            this.articlesService.updateArticle(articleID, title, description, categories, content,
                    authentication.getName());
        }
        return "redirect:/";
    }

    @GetMapping("/article/like/{articleID}")
    public String likeVideo(HttpServletRequest request, Model model, @PathVariable long articleID) {

        Article articleToAdd = articlesService.findArticleById(articleID);

        User userToChange = userService.getUserByName(request.getUserPrincipal().getName());

        if (userToChange != null) {
            userToChange.addLikedArticle(articleToAdd);
        }
        articleToAdd.addLikeArticleEntity(userToChange);
        articlesService.updateArticle(articleToAdd);
        userService.updateUser(userToChange);
        String articleURL = "redirect:/article/" + articleID;

        return articleURL;

    }

    @GetMapping("/article/unlike/{articleID}")
    public String unlikeVideo(HttpServletRequest request, Model model, @PathVariable long articleID) {

        Article articleToAdd = articlesService.findArticleById(articleID);

        User userToChange = userService.getUserByName(request.getUserPrincipal().getName());

        if (userToChange != null && articleToAdd != null) {
            userToChange.removeLikedArticle(articleToAdd);
            articleToAdd.removeLikeVideoEntity(userToChange);
        }

        articlesService.updateArticle(articleToAdd);
        userService.updateUser(userToChange);
        String videoURL = "redirect:/article/" + articleID;

        return videoURL;

    }

}