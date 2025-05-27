package com.zzt.blog.controller;

import com.zzt.blog.dto.CommentDTO;
import com.zzt.blog.entity.Comment;
import com.zzt.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 评论控制器
@RestController
@RequestMapping("/comments")
@Tag(name = "评论管理", description = "评论管理接口")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{articleId}")
    @Operation(summary = "根据文章ID获取评论列表", description = "根据文章ID获取评论列表")
    public List<Comment> listCommentsByArticleId(@PathVariable Long articleId) {
        return commentService.listCommentsByArticleId(articleId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取评论", description = "根据ID获取评论")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    @Operation(summary = "保存评论", description = "保存评论")
    public void saveComment(@RequestBody CommentDTO comment) {
        commentService.saveComment(comment);
    }

    @PutMapping
    @Operation(summary = "[待修改]更新评论", description = "更新评论")
    public void updateComment(@RequestBody Comment comment) {
        commentService.updateComment(comment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论", description = "删除评论")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}