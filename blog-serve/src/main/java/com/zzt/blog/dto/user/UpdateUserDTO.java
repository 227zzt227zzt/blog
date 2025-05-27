package com.zzt.blog.dto.user;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UpdateUserDTO {
    @TableId
    private Long id;
    private String username;
    private String password;
    private String avatar;
    private String bio;
    private String email;
}
