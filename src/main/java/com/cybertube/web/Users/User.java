package com.cybertube.web.Users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.cybertube.web.Articles.Article;
import com.cybertube.web.Comments.Comment;
import com.cybertube.web.Videos.Video;
import org.hibernate.validator.constraints.Length;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id_user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authorVideoEntity")
    private List<Video> videos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authorArticleEntity")
    private List<Article> articles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authorCommentEntity")
    private List<Comment> commentaries;

    @Column(unique = true, nullable = false)
    @Length(min = 4)
    private String username;

    @Column(unique = true, nullable = false)
    @Length(min = 7)
    private String email;

    @Length(min = 6, max = 100)
    @Column(nullable = false)
    private String pass;

    @Length(max = 20)
    @Column(nullable = false)
    private String Role;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "LikeArticleEntity")
    private List<Article> likedArticles;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "LikeVideoEntity")
    private List<Video> likedVideo;

    public List<Article> getLikedArticles() {
        return this.likedArticles;
    }

    public User() {
        this.articles = new ArrayList<>();
        this.videos = new ArrayList<>();
        this.likedArticles = new ArrayList<>();
        this.likedVideo = new ArrayList<>();
        this.Role = "ROLE_USER";
    }

    public User(String username, String email, String pass) {
        this();
        this.username = username;
        this.email = email;
        this.pass = pass;
        this.Role = "ROLE_USER";
    }

    public Long getId_user() {
        return this.id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public List<Video> getVideos() {
        return this.videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Comment> getComment() {
        return this.commentaries;
    }

    public void setComment(List<Comment> commentaries) {
        this.commentaries = commentaries;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRole() {
        return this.Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public void setLikedArticles(List<Article> likedArticles) {
        this.likedArticles = likedArticles;
    }

    public List<Video> getLikedVideo() {
        return this.likedVideo;
    }

    public void setLikedVideo(List<Video> likedVideo) {
        this.likedVideo = likedVideo;
    }

    public void addLikedVideo(Video video) {
        this.likedVideo.add(video);
    }

    public void addLikedArticle(Article article) {
        this.likedArticles.add(article);
    }

    public void removeLikedVideo(Video video) {
        this.likedVideo.remove(video);
    }

    public void removeLikedArticle(Article article) {
        this.likedArticles.remove(article);
    }

}