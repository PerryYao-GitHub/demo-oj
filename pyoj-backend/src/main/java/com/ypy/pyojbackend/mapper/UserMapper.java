package com.ypy.pyojbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ypy.pyojbackend.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
