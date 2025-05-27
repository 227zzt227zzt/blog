package com.zzt.blog.controller;

import com.zzt.blog.dto.user.LoginDTO;
import com.zzt.blog.dto.user.RegisterDTO;
import com.zzt.blog.dto.user.UpdateUserDTO;
import com.zzt.blog.entity.User;
import com.zzt.blog.entity.UserRole;
import com.zzt.blog.service.UserRoleService;
import com.zzt.blog.service.UserService;
import com.zzt.blog.util.JwtUtils;
import com.zzt.blog.util.Result;
import com.zzt.blog.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 227
 */ // 用户控制器
@RestController
@RequestMapping("/users")
@Tag(name = "用户管理")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情")
    public Result<UserVO> getUserById(@PathVariable Long id) {
        if(id== null) {
            return Result.error(500, "用户ID为空");
        }
        User user = userService.getUserById(id);
        if(user== null) {
            return Result.error(500, "用户不存在");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return Result.success("获取成功", userVO);
    }

    @PostMapping("/register")
    @Operation(summary = "注册用户")
    public Result<Void> registerUser(@RequestBody RegisterDTO user) {
        if(user== null) {
            return Result.error(500, "用户为空");
        }
       User user1 =  userService.registerUser(user);
        if(user1== null) {
            return Result.error(500, "注册失败");
        }
        //  返回结果,赋予角色
        UserRole userRole = new UserRole();
        userRole.setUserId(user1.getId());
        userRole.setRoleId(2);
        userRoleService.assignRole(userRole);
        return Result.success("注册成功");
    }

    @PostMapping("/login")
    @Operation(summary = "登录用户")
    public Result<Map<String, Object>> loginUser(@RequestBody LoginDTO loginDTO) {
        if(loginDTO== null) {
            return Result.error(500, "登录信息为空");
        }

        User user = userService.loginUser(loginDTO);
        if(user== null) {
            return Result.error(500, "登录失败");
        }
        List<UserRole> roles = userRoleService.getRolesByUserId(user.getId());
        // 生成JWT令牌
        String token = jwtUtils.generateToken(user.getUsername(), user.getId(), roles);
        
        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        
        return Result.success("登录成功", result);
    }

    @PutMapping
    @Operation(summary = "更新用户信息")
    public void updateUser(@RequestBody UpdateUserDTO user) {
        userService.updateUser(user);
    }

    @GetMapping("/UserPage")
    @Operation(summary = "分页获取用户列表")
    public Result<Map<String, Object>> userPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                @RequestParam(required = false, defaultValue = "10") Integer size) {
        Map<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("size", size);
        result.put("users", userService.getUserPage(page, size));
        return Result.success("获取成功", result);
}

}