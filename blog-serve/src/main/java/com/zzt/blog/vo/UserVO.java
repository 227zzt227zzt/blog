package com.zzt.blog.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVO {
    private String username;
    private String password;
    private String avatar;
    private String bio;
    private String email;
    private Date createTime;
    private Date lastLogin;
}
