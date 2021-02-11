package com.cybertube.web.Users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cybertube.web.Articles.Article;
import com.cybertube.web.Articles.ArticlesController;
import com.cybertube.web.Articles.ArticlesService;
import com.cybertube.web.Videos.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(final Model model, final HttpServletRequest request, final HttpSession session,
            final RedirectAttributes redirectAttrs) {

        return "login";

    }

    @GetMapping("/profile")
    public String userprofile(final Model model, final HttpServletRequest request) {
        User userToShow = userService.getUserByUsername(request.getUserPrincipal().getName());
        model.addAttribute("user", userToShow);
        model.addAttribute("article", userToShow.getArticles());
        model.addAttribute("video", userToShow.getVideos());
        model.addAttribute("comment", userToShow.getComment());
        model.addAttribute("likedVideos", userToShow.getLikedVideo());
        model.addAttribute("likedArticles", userToShow.getLikedArticles());

        return "user";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        model.addAttribute("user", userService.getAll());
        model.addAttribute("article", articlesService.findAllArticles());
        model.addAttribute("video", videoService.findAll());
        return "admin";
    }

    @RequestMapping(value = "/user/deleteUser/{username}", method = { RequestMethod.GET })
    public String deleteUser(final HttpServletRequest request, @PathVariable final String username) {
        if (request.isUserInRole("ROLE_ADMIN") || this.userService.getUserByUsername(username).getUsername()
                .equalsIgnoreCase(request.getUserPrincipal().getName())) {
            this.userService.deleteUserById(this.userService.getUserByUsername(username).getId_user());
            return "delete_confirm";
        } else {
            return "error";
        }
    }

    @GetMapping("/loginerror")
    public String loginFailed(final Model model, final HttpServletRequest request,
            final RedirectAttributes redirectAttrs) {
        return "loginerror";
    }

    /*
     * @GetMapping("/register") public String register(Model model,
     * HttpServletRequest request) { // TOKEN CsrfToken token = (CsrfToken)
     * request.getAttribute("_csrf"); model.addAttribute("token", token.getToken());
     * return "/portal/register"; }
     */
    @PostMapping("/register")
    public String newArticle(@RequestParam final String username, @RequestParam final String email,
            @RequestParam final String password, final HttpServletRequest request, final Model model,
            final RedirectAttributes redirectAttrs) {

        try {
            /*
             * ValidationResult result = recaptchaValidator.validate(request); // TOKEN
             * CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
             * model.addAttribute("token", token.getToken());
             */
            // Nombre de usuario ya cogido
            if (userService.getUserByUsername(username) != null) {
                redirectAttrs.addFlashAttribute("usernameTaken", true);
                return "redirect:/login";
            }
            // Email ya cogido
            if (userService.getUserByEmail(email) != null) {
                redirectAttrs.addFlashAttribute("emailTaken", true);
                return "redirect:/login";
            }
            final User existingUser = userRepository.findByEmailIgnoreCase(email);
            if (existingUser != null) {
                return "error";
            } else {
                final User test = new User(username, email, BCrypt.hashpw(password, BCrypt.gensalt()));
                userRepository.save(test);

                return "redirect:/";
            }
        } catch (final Exception e) {
            redirectAttrs.addFlashAttribute("invalid", true);
            return "redirect:/login";
        }

    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam final String username, @RequestParam final String email,
            @RequestParam final String pass, @RequestParam final String new_pass, Model model,
            final HttpServletRequest request) {
        this.userService.updateUser(request.getUserPrincipal().getName(), username, email, pass, new_pass);
        return "redirect:/";
    }
}
