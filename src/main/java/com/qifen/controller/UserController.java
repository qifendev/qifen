package com.qifen.controller;

import com.qifen.SevenUtil;
import com.qifen.model.User;
import com.qifen.repository.UserRepository;
import com.qifen.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 映射log.html用户登陆界面
 */

@Controller
public class UserController {
    @Autowired
    MailService mailService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/log")//登录注册跳转
    public String user(@RequestParam(name = "act", defaultValue = "login") String act, Model model) {
        String title = "";
        if ("sign".equals(act)) {
            title = "注册-大可图库";
        } else if ("login".equals(act)) {
            title = "登录-大可图库";
        }
        model.addAttribute("title", title);
        model.addAttribute("act", act);
        return "log";
    }

    @GetMapping("/signEmail")//发送验证码
    @ResponseBody
    public String signEmail(@RequestParam(name = "email") String email) {
        if (!SevenUtil.regex(SevenUtil.regexMail, email)) {
            return "邮箱格式有问题";
        }
        return mailService.sendVerifyCodeMail(email);
    }

    @PostMapping("/sign")//提交注册数据
    @ResponseBody
    public String sign(@RequestParam(name = "mail") String mail,
                       @RequestParam(name = "code") int code,
                       @RequestParam(name = "pas") String pas,
                       @RequestParam(name = "conPas") String conPas) {
        if (SevenUtil.regex(SevenUtil.regexMail, mail)) {
            if (pas.equals(conPas) && SevenUtil.regex(SevenUtil.regexPassword, pas)) {
                User isExistUser = userRepository.findByUserMail(mail);
                if (isExistUser != null) {
//                    if (isExistUser.getUserPassword() == null) {
                    if (isExistUser.getUserVerifyCode() == code) {
                        if (isExistUser.getUserVerifyTime() > SevenUtil.getTime()) {
                            isExistUser.setUserMail(mail);
                            isExistUser.setUserPassword(pas);
                            userRepository.save(isExistUser);
                        } else {
                            return "验证码过期";
                        }
                    } else {
                        return "验证码错误";
                    }
//                    } else {
//                        return "账户已经注册，找回密码成功";
//                    }
                } else {
                    return "请先发送验证码";
                }
            } else {
                return "两次密码不同或者密码格式不正确";
            }
        } else {
            return "邮箱格式有问题";
        }
        return "注册/改密大可图库成功";
    }

    @PostMapping("/login")//提交登录数据
    @ResponseBody
    public String sign(HttpServletResponse httpServletResponse,
                       HttpServletRequest httpServletRequest,
                       @RequestParam(name = "mail") String mail,
                       @RequestParam(name = "pas") String pas) {
        if (SevenUtil.regex(SevenUtil.regexMail, mail)) {
            if (SevenUtil.regex(SevenUtil.regexPassword, pas)) {
                User user = userRepository.findByUserMailAndUserPassword(mail, pas);
                long time = SevenUtil.getTime();
                if (user != null) {
                    if (user.getUserCanLogin() < time) {
                        String token = SevenUtil.getUUID();
                        user.setUserLastLoginTime(time);
                        user.setUserToken(token);
                        user.setUserTokenTime(time + 2592000000L);
                        userRepository.save(user);
                        SevenUtil.setCookie(httpServletRequest, httpServletResponse, "token", token, 60 * 60 * 24 * 7);
                    } else {
                        return user.getUserNotCanLoginInfo() + SevenUtil.millisToDate(user.getUserCanLogin());
                    }
                } else {
                    return "账号不存在或者密码错误";
                }
            } else {
                return "密码格式错误";
            }
        } else {
            return "邮箱格式错误";
        }
        return "登录成功";
    }

    @GetMapping("/isLog")//点击主页登录按钮查看是否登录
    public String isLogin(HttpServletRequest httpServletRequest) {
        String token = SevenUtil.getCookies(httpServletRequest).get("token");
        if (token != null) {
            User user = userRepository.findByUserToken(token);
            if (user != null)
                if (user.getUserTokenTime() > SevenUtil.getTime())
                    return "redirect:/my";
        }
        return "redirect:/log";
    }


    @GetMapping("/logout")//注销
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SevenUtil.clearAllCookies(httpServletRequest, httpServletResponse);
        return "redirect:/";
    }
}
