package com.lwh.controller;

import com.lwh.service.MailService;
import com.lwh.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    MailService mailService;

    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpSession session){
        mailService.sendMimeMail(email,session);
        return "success";
    }

    @PostMapping("/regist")
    @ResponseBody
    public String regist(UserVo userVo,HttpSession session){
        mailService.register(userVo,session);
        return "success";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(String email,String password){
        mailService.loginIn(email, password);
        return "success";
    }

}
