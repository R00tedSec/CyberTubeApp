package com.cybertube.web.Comments;

import javax.persistence.*;

import com.cybertube.web.Articles.Article;
import com.cybertube.web.Articles.ArticlesRepository;
import com.cybertube.web.Articles.ArticlesService;
import com.cybertube.web.Users.User;
import com.cybertube.web.Users.UserRepository;
import com.cybertube.web.Videos.Video;
import com.cybertube.web.Videos.VideoRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Comentarios")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long commentID;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User authorCommentEntity;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Comment() {
    }

    // Constructor para video
    public Comment(String content, Video video, User user) {
        this.content = content;
        this.authorCommentEntity = user;
        this.video = video;

    }

    // Constructor para articulo
    public Comment(String content, Article article, User user) {
        this.content = content;
        this.authorCommentEntity = user;
        this.article = article;
    }

    public User getAuthorCommentEntity() {
        return this.authorCommentEntity;
    }

    public void setAuthorCommentEntity(User authorCommentEntity) {
        this.authorCommentEntity = authorCommentEntity;
    }

    public Video getVideo() {
        return this.video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}