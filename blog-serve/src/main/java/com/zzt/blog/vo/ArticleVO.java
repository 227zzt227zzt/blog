package com.zzt.blog.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzt.blog.enums.ArticleStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleVO {
    @TableId
    private Long id;
    private Long authorId;
    private String author;
    private String title;
    private String summary;
    private String coverImage;
    private String content;
    private Long categoryId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private Date updateTime;

    private Integer viewCount;

}
