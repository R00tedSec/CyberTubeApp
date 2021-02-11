package com.cybertube.web.Articles;

import java.util.*;
import javax.persistence.*;

import com.cybertube.web.Comments.Comment;
import com.cybertube.web.Users.User;
import com.cybertube.web.Users.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.beans.factory.annotation.Autowired;

import java.text.*;

@Entity
@Table(name = "Articulos")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long articleID;

    @Column(unique = true, nullable = false)
    private String articleTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String articleDescription;

    @ElementCollection
    private List<String> articleCategories;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String articleContent;

    @Column(nullable = false)
    private String articleDate;

    @Column(nullable = false)
    private String articleAuthor;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> commentaries;

    @JsonIgnore
    @ManyToOne
    private User authorArticleEntity;

    @JsonIgnore
    @ManyToMany
    private List<User> LikeArticleEntity;

    public Article() {
        this.articleCategories = new ArrayList<String>();
        this.commentaries = new ArrayList<Comment>();
        this.LikeArticleEntity = new ArrayList<User>();
    }

    public Article(String articleTitle, String articleDescription, List<String> articleCategories,
            String articleContent, User author) {
        this.articleTitle = articleTitle;
        this.articleDescription = articleDescription;
        this.articleCategories = articleCategories;
        this.articleContent = articleContent;
        this.articleDate = Article.getCurrentTime();
        this.articleAuthor = author.getUsername();
        this.authorArticleEntity = author;

    }

    public long getArticleID() {
        return this.articleID;
    }

    public void setArticleID(long articleID) {
        this.articleID = articleID;
    }

    public List<User> getLikeArticleEntity() {
        return this.LikeArticleEntity;
    }

    public void setLikeArticleEntity(List<User> LikeArticleEntity) {
        this.LikeArticleEntity = LikeArticleEntity;
    }

    public void addLikeArticleEntity(User user) {
        this.LikeArticleEntity.add(user);
    }

    public String getArticleTitle() {
        return this.articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public void removeLikeVideoEntity(User user) {
        this.LikeArticleEntity.remove(user);
    }

    public String getArticleDescription() {
        return this.articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public List<String> getArticleCategories() {
        return this.articleCategories;
    }

    public void setArticleCategories(List<String> articleCategories) {
        this.articleCategories = articleCategories;
    }

    public String getArticleContent() {
        return this.articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleDate() {
        return this.articleDate;
    }

    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }

    public String getArticleAuthor() {
        return this.articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public static String getCurrentTime() {
        Date date = new Date();
        String strDateFormat = "dd MMMM, YYYY";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public List<Comment> getCommentaries() {
        return this.commentaries;
    }

    public void setCommentaries(List<Comment> commentaries) {
        this.commentaries = commentaries;
    }

    public void addCommentary(Comment commentary) {
        this.commentaries.add(commentary);
    }

    public void deleteCommentary(Comment commentary) {
        // this.commentaries.remove(commentary);
    }

}
