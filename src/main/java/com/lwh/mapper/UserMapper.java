package com.lwh.mapper;

import com.lwh.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    //注册用户
    void insertUser(User user);

    //根据邮箱查询
    User queryByEmail(String email);

}

