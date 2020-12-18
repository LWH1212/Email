package com.lwh.service;

import com.lwh.entity.User;
import com.lwh.vo.UserVo;

public class UserVoToUser {

    //表单中的对象转化为数据库中存储的用户对象（剔除表单中的code）
    public static User toUser(UserVo userVo){

        //创建一个数据库中存储的对象
        User user = new User();

        //传值
        user.setUsername(userVo.getUsername());
        user.setPassword(userVo.getPassword());
        user.setEmail(userVo.getEmail());

        //返回包装后的对象
        return user;
    }

}
