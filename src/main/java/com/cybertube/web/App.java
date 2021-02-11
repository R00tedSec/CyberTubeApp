package com.cybertube.web;

import com.cybertube.web.Articles.ArticlesService;
import com.cybertube.web.Videos.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class App {

    @Autowired
    private VideoService videoService;
    @Autowired
    private ArticlesService articleService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("videos", videoService.findLastVideos());
        model.addAttribute("articles", articleService.findLastArticles());
        return "index.html";
    }

    @GetMapping("/help")
    public String help() {
        return "ayuda";
    }
    

}
