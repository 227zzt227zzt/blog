package com.zzt.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzt.blog.dto.user.LoginDTO;
import com.zzt.blog.dto.user.RegisterDTO;
import com.zzt.blog.dto.user.UpdateUserDTO;
import com.zzt.blog.entity.User;

public interface UserService {
    User getUserById(Long id);
    User getUserByUsername(String username);
    User registerUser(RegisterDTO user);
    User loginUser(LoginDTO loginDTO);
    void updateUser(UpdateUserDTO user);
    void disableUser(Long userId);

     Page<User> getUserPage(Integer page, Integer size);
}