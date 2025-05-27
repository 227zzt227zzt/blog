package com.zzt.blog.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.zzt.blog.enums.ArticleStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleDTO {
    private String title;
    private String summary;
    private String coverImage;
    private String content;
    private Long categoryId;
    /**
     * 0-草稿，1-发布
     */
    private ArticleStatus status;
}
