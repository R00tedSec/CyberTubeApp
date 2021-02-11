package com.cybertube.web.Videos;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.cybertube.web.Comments.CommentService;
import com.cybertube.web.Users.User;
import com.cybertube.web.Users.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    /**
     * @PostConstruct public void init() {
     * 
     *                }
     */
    @GetMapping("/videos")
    public String allVideos(Model model) {
        model.addAttribute("videos_list", videoService.findAll());
        return "videos";
    }

    @GetMapping("/video/{videoID}")
    public String getVideo(HttpServletRequest request, Model model, @PathVariable long videoID) {
        Video videotoshow = videoService.findById(videoID);
        User actualUser = null;
        model.addAttribute("video", videotoshow);
        model.addAttribute("comment_list", videotoshow.getCommentaries());
        if (request.getUserPrincipal() != null) {
            actualUser = userService.getUserByName(request.getUserPrincipal().getName());

            model.addAttribute("check",
                    request.getUserPrincipal().getName().equalsIgnoreCase(videotoshow.getVideoAuthor()));
        } else {
            model.addAttribute("check", false);
        }
        if (actualUser != null) {
            model.addAttribute("checkLogged", true);
            System.out.println(actualUser.getRole());
            if (videotoshow.getLikeVideoEntity().contains(actualUser)) {
                model.addAttribute("checkLike", true);
            } else {
                model.addAttribute("checkLike", false);
            }
        } else {
            model.addAttribute("checkLogged", false);
        }
        return "video";
    }

    @GetMapping("/postVideo")
    public String getPostVideo() {
        return "postVideo";
    }

    @PostMapping("/postVideo")
    public String addVideo(Authentication authentication, Model model, @RequestParam String title,
            @RequestParam String URL, @RequestParam String description, @RequestParam List<String> category) {
        Video newVideo = new Video(title, URL, description, userService.getUserByUsername(authentication.getName()),
                category);
        this.videoService.saveVideo(newVideo);

        return "redirect:/";
    }

    @RequestMapping(value = "/video/deleteVideo/{videoID}", method = { RequestMethod.GET })
    public String deleteVideo(@PathVariable long videoID, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN") || this.videoService.findById(videoID).getVideoAuthor()
                .equalsIgnoreCase(request.getUserPrincipal().getName())) {
            this.videoService.removeVideo(videoID);
            return "delete_confirm";

        } else {
            return "error";
        }
    }

    @RequestMapping(value = "/video/updateVideo/{videoID}", method = { RequestMethod.GET })
    public String updateForm(Model model, @PathVariable long videoID) {
        // Cogemos el vídeo a modificar
        model.addAttribute("video", this.videoService.findById(videoID));
        return "modify_video";
    }

    @PostMapping("/updateVideo/{videoID}")
    public String updateVideo(HttpServletRequest request, Authentication authentication, Model model,
            @PathVariable long videoID, @RequestParam String title, @RequestParam String URL,
            @RequestParam String description, @RequestParam List<String> category) {
        if (request.isUserInRole("ROLE_ADMIN") || this.videoService.findById(videoID).getVideoAuthor()
                .equalsIgnoreCase(request.getUserPrincipal().getName())) {
            this.videoService.updateVideo(videoID, title, URL, description, authentication.getName(), category);
        }
        return "redirect:/";
    }

    @GetMapping("/video/like/{videoID}")
    public String likeVideo(HttpServletRequest request, Model model, @PathVariable long videoID) {

        Video videoToAdd = videoService.findById(videoID);

        User userToChange = userService.getUserByName(request.getUserPrincipal().getName());

        if (userToChange != null) {
            userToChange.addLikedVideo(videoToAdd);
        }
        videoToAdd.addLikeVideoEntity(userToChange);
        videoService.updateVideo(videoToAdd);
        userService.updateUser(userToChange);
        String videoURL = "redirect:/video/" + videoID;

        return videoURL;

    }

    @GetMapping("/video/unlike/{videoID}")
    public String unlikeVideo(HttpServletRequest request, Model model, @PathVariable long videoID) {

        Video videoToAdd = videoService.findById(videoID);

        User userToChange = userService.getUserByName(request.getUserPrincipal().getName());

        if (userToChange != null && videoToAdd != null) {
            userToChange.removeLikedVideo(videoToAdd);
            videoToAdd.removeLikeVideoEntity(userToChange);
        }

        videoService.updateVideo(videoToAdd);
        userService.updateUser(userToChange);
        String videoURL = "redirect:/video/" + videoID;

        return videoURL;

    }
}