package com.cybertube.web.Comments;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByCommentID(long id);

    // List<Comment> findByAuthorAllComments(String author);
}
