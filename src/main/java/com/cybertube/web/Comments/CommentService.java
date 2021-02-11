package com.cybertube.web.Comments;

import java.util.List;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EntityManager entityManager;

    public void saveComment(Comment comment) {
        this.commentRepository.saveAndFlush(comment);
    }

    public Comment getCommentById(Long id) {
        return this.commentRepository.findById(id).get();
    }

    public void deleteCommentById(Long id) {
        this.commentRepository.deleteById(id);
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

}