package com.qifen.service;

import com.qifen.SevenUtil;
import com.qifen.model.User;
import com.qifen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    //是否登录
    public String isLogin(String token) {
        User user = userRepository.findByUserToken(token);
        if (user != null) {
            if (user.getUserTokenTime() + SevenUtil.getTime() > 0) {
                return "已经登录";
            } else {
                return "token过期";
            }
        } else {
            return "token不存在";
        }
    }

    //根据浏览器的cookie里的token来查用户并返回用户对象
    public User tokenToUser(HttpServletRequest httpServletRequest) {
        String token = SevenUtil.getCookies(httpServletRequest).get("token");
        if (token != null) {
            User user = userRepository.findByUserToken(token);
            return user;
        } else {
            return null;
        }
    }
}
