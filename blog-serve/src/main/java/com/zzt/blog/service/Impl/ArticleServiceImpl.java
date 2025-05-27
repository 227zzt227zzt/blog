package com.zzt.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzt.blog.dto.ArticleDTO;
import com.zzt.blog.entity.Article;
import com.zzt.blog.mapper.ArticleMapper;
import com.zzt.blog.service.ArticleService;
import com.zzt.blog.service.ArticleTagService;
import com.zzt.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 227
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public List<Article> listArticles() {
        return this.list();
    }

    @Override
    public Article getArticleById(Long id) {
        return this.getById(id);
    }

    @Transactional
    @Override
    public void saveArticle(ArticleDTO article) {
        if (article == null) {
            return;
        }
        Article articleEntity = new Article();
        BeanUtils.copyProperties(article, articleEntity);
        articleEntity.setCreateTime(new Date());
        articleEntity.setUpdateTime(new Date());
        articleEntity.setViewCount(0);
        articleEntity.setIsTop(false);
        this.save(articleEntity);
    }

    @Transactional
    @Override
    public void updateArticle(Article article) {
        this.updateById(article);
    }
@Autowired
private ArticleTagService articleTagService;
@Autowired
private CommentService commentService;

    @Transactional
    @Override
    public void deleteArticle(Long id) {
        // 删除文章时，同时删除文章标签关系,还有文章和分类关系，评论等
        articleTagService.deleteByArticleId(id);
        commentService.deleteCommentsByArticleId(id);
        this.removeById(id);
    }

    @Override
    public List<Article> getTopArticles() {
        return this.list(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsTop, true)
                .orderByDesc(Article::getUpdateTime));
    }
}