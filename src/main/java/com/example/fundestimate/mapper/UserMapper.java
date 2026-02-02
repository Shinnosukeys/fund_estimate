package com.example.fundestimate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.fundestimate.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
