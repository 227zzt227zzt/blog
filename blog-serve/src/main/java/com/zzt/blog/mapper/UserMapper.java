package com.zzt.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzt.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 227
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}