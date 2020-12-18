package com.lwh.service;

import com.lwh.entity.User;
import com.lwh.mapper.UserMapper;
import com.lwh.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserMapper userMapper;

    @Value("${spring.mail.username}")
    private String form;

    //给前端的邮箱发送验证码
    public boolean sendMimeMail(String email, HttpSession session){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            //主题
            mailMessage.setSubject("验证码邮件");
            //生成随机数
            String code = randomCode();

            //将随机数放置到session中
            session.setAttribute("email",email);
            session.setAttribute("code",code);
            mailMessage.setText("您收到的验证码是："+code);//内容
            mailMessage.setTo(email);//发给谁
            mailMessage.setFrom(form);//你自己的邮箱
            mailSender.send(mailMessage);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //随机生成验证码
    private String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i=0;i<6;i++){
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    //检验验证码是否一致
    public boolean register(UserVo userVo, HttpSession session){
        //获取session中的验证信息
        String email = (String) session.getAttribute("email");
        String code = (String) session.getAttribute("code");

        //获取表单中的提交的验证信息
        String voCode = userVo.getCode();

        //如果email数据为空，或者不一致，注册失败
        if (email == null || email.isEmpty()){
            return false;
        }else if (!code.equals(voCode)){
            return false;
        }

        //保存数据
        User user = UserVoToUser.toUser(userVo);

        //将数据写入数据库
        userMapper.insertUser(user);

        //跳转成功页面
        return true;

    }

    public boolean loginIn(String email,String password){
        User user = userMapper.queryByEmail(email);
        if (!user.getPassword().equals(password)){
            return false;
        }
        System.out.println("登录成功：数据库密码是："+user.getPassword());
        return true;
    }

}
