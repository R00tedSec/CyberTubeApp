package com.cybertube.web.Comments;

import javax.servlet.http.HttpServletRequest;

import com.cybertube.web.Articles.ArticlesService;
import com.cybertube.web.Users.UserService;
import com.cybertube.web.Videos.Video;
import com.cybertube.web.Videos.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticlesService articlesService;

    @PostMapping("/article/comment")
    public String addCommentArticle(Authentication authentication, Model model, @RequestParam Long ArticleID,
            @RequestParam String content) {

        Comment newComment = new Comment(content, articlesService.findArticleById(ArticleID),
                userService.getUserByName(authentication.getName()));

        articlesService.findArticleById(ArticleID).addCommentary(newComment);

        this.commentService.saveComment(newComment);

        return "redirect:/articles";
    }

    @PostMapping("/video/comment")
    public String addCommentVideo(Authentication authentication, Model model, @RequestParam Long videoID,
            @RequestParam String content) {

        Comment newComment = new Comment(content, videoService.findById(videoID),
                userService.getUserByName(authentication.getName()));

        videoService.findById(videoID).addCommetary(newComment);

        this.commentService.saveComment(newComment);

        return "redirect:/videos";
    }


      @RequestMapping(value = "/comment/delete/{commentID}", method = { RequestMethod.GET }) public String
      deleteCommentary(HttpServletRequest request, @PathVariable long commentID) {
      if (request.isUserInRole("ROLE_ADMIN") ||
      this.commentService.getCommentById(commentID).getAuthorCommentEntity()
      .getUsername().equalsIgnoreCase(request.getUserPrincipal().getName())) {
      this.commentService.deleteCommentById(commentID); } return
      "redirect:/videos"; }
     
}