package com.qifen.service;

import com.qifen.SevenUtil;
import com.qifen.model.User;
import com.qifen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    UserRepository userRepository;

    public String sendVerifyCodeMail(String email) {//发送验证码到邮箱
        SimpleMailMessage msg = new SimpleMailMessage();
        int code = SevenUtil.getCode();//获取随机验证码100000-999999
        msg.setFrom("858810078@qq.com");//发送方地址
        msg.setTo(email);//接收方地址
        msg.setSubject(code + "-验证码" + "-大可图库");//标题
        msg.setText("欢迎使用大可图库,你的验证码是" + code + ",验证码有效期1分钟。");//内容
        try {
            javaMailSender.send(msg);//发送
        } catch (MailException e) {
            System.err.println(e.getMessage());
            return "发送验证码到邮箱失败";
        }
        User isExistUser = userRepository.findByUserMail(email);
        if (isExistUser == null) {
            User user = new User();
            user.setUserMail(email);
            user.setUserVerifyCode(code);
            user.setUserVerifyTime(SevenUtil.getTime() + 62000);
            userRepository.save(user);
        } else {
            isExistUser.setUserVerifyCode(code);
            isExistUser.setUserVerifyTime(SevenUtil.getTime() + 62000);
            isExistUser.setUserSignTime(SevenUtil.getTime());
            userRepository.save(isExistUser);
        }
        return "发送验证码到邮箱成功";
    }

}
