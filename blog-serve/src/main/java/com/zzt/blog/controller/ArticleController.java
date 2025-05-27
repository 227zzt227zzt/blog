package com.zzt.blog.controller;

import com.zzt.blog.dto.ArticleDTO;
import com.zzt.blog.entity.Article;
import com.zzt.blog.service.ArticleService;
import com.zzt.blog.util.Page;
import com.zzt.blog.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 227
 */
@RestController
@RequestMapping("/articles")
@Tag(name = "文章管理")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping
    @Operation(summary = "获取文章列表")
    public Result<Page<Article>> listArticles(@Parameter(description = "页码", required = true) @RequestParam(defaultValue = "1") Integer currentPage, @Parameter(description = "每页数量", required = true) @RequestParam(defaultValue = "3") Integer size) {
        //获取文章列表，然后分页返回
        List<Article> articles = articleService.listArticles();
        Page<Article> page = Page.listToPage(articles, currentPage, size);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文章详情")
    public Result<Article> getArticleById(@PathVariable Long id) {
        if (id == null) {
            return Result.error("参数错误");
        }
        if(articleService.getArticleById(id) == null)
            return Result.error("文章不存在");
        return Result.success(articleService.getArticleById(id));
    }


    @PostMapping
    @Operation(summary = "保存文章")
    public Result<Void> saveArticle(@RequestBody ArticleDTO article) {
        if (article == null) {
            return Result.error("参数错误");
        }
        articleService.saveArticle(article);
        return Result.success();

    }

    @PutMapping
    @Operation(summary = "更新文章")
    public Result<Void> updateArticle(@RequestBody Article article) {
        if (article == null) {
            return Result.error("参数错误");
        }
        articleService.updateArticle(article);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章")
    public Result<Void> deleteArticle(@PathVariable Long id) {
        if (id == null) {
            return Result.error("参数错误");
        }
        articleService.deleteArticle(id);
        return Result.success();
    }
}