package com.zzt.blog.service;

import com.zzt.blog.dto.CommentDTO;
import com.zzt.blog.entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> listCommentsByArticleId(Long articleId);
    Comment getCommentById(Long id);
    void saveComment(CommentDTO comment);
    void updateComment(Comment comment);
    void deleteComment(Long id);
    List<Comment> getPendingComments();

    void deleteCommentsByArticleId(Long id);
}