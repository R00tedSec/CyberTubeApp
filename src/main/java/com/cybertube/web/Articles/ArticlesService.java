package com.cybertube.web.Articles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService {

    @Autowired
    private ArticlesRepository articlesRepository;

    public void addArticle(final Article article) {
        this.articlesRepository.save(article);
    }

    public void removeArticle(final long articleID) {
        if (articlesRepository.existsById(articleID)) {
            articlesRepository.deleteById(articleID);
        }
    }

    public void updateArticle(final long articleID, String title, String description, List<String> categories,
            String content, String author) {
        if (this.articlesRepository.existsById(articleID)) {
            Article auxArticle = this.articlesRepository.getOne(articleID);
            if (!title.equals(null))
                auxArticle.setArticleTitle(title);

            if (!description.equals(null))
                auxArticle.setArticleDescription(description);

            if (!categories.equals(null))
                auxArticle.setArticleCategories(categories);

            if (!content.equals(null))
                auxArticle.setArticleContent(content);

            if (!author.equals(null))
                auxArticle.setArticleAuthor(author);

            this.articlesRepository.saveAndFlush(auxArticle);
        }
    }

    public Article findArticleById(final long articleID) {
        return articlesRepository.findById(articleID).get();
    }

    public List<Article> findAllArticles() {
        return articlesRepository.findAll();
    }

    public List<Article> findLastArticles() {
        List<Article> lastArticles = new ArrayList<>();

        if (articlesRepository != null) {
            List<Article> allArticles = articlesRepository.findAll();
            if (allArticles.size() > 2) {
                for (int i = allArticles.size() - 1; i > allArticles.size() - 4; i--) {
                    lastArticles.add(allArticles.get(i));
                }
            }
        }
        return lastArticles;
    }

    public void updateArticle(Article article) {
        if (this.articlesRepository.existsById((article.getArticleID()))) {
            this.articlesRepository.saveAndFlush(article);

        }

    }

}
