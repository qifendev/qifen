package com.qifen.controller;


import com.qifen.SevenUtil;
import com.qifen.model.Picture;
import com.qifen.model.User;
import com.qifen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 登陆后可通过此类修改信息
 */

@Controller
public class ModController {
    @Autowired
    UserRepository userRepository;
    @Value("${duck.path}")
    String path;

    @PostMapping("/avatar")
    @ResponseBody
    public String avatar(HttpServletRequest httpServletRequest) {
        String token = SevenUtil.getCookies(httpServletRequest).get("token");
        if (token != null) {
            User user = userRepository.findByUserToken(token);
            if (user != null) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) httpServletRequest;
                MultipartFile multipartFile = multipartRequest.getFile("file");
                if (multipartFile != null) {
                    if (multipartFile.getOriginalFilename() != null) {
                        if (!SevenUtil.isImage(multipartFile.getOriginalFilename())) {
                            return "不支持的图片类型或未上传图片";
                        }
                        if (multipartFile.getSize() > 10485760) {
                            return "图片最大10M";
                        }
                    }
                    try {
                        String fileName = multipartFile.getOriginalFilename();
                        if (fileName != null) {
                            fileName = SevenUtil.getUUID() + fileName.substring(fileName.lastIndexOf("."));
                        }
                        File saveFileName = new File(path + "/avatar/" + fileName);
                        multipartFile.transferTo(saveFileName);
                        SevenUtil.delFile(path, user.getUserAvatar(), "/avatar/");
                        user.setUserAvatar(fileName);
                        userRepository.save(user);
                        return "上传头像成功";

                    } catch (Exception e) {
                        e.printStackTrace();
                        return e.getMessage();
                    }
                } else {
                    return "未上传图片";
                }
            } else {
                return "token不可用";
            }
        }
        return "用户未登录";
    }


    @GetMapping("/nick")
    @ResponseBody
    public String nick(HttpServletRequest httpServletRequest,
                       @RequestParam(value = "nick") String nick) {
        String token = SevenUtil.getCookies(httpServletRequest).get("token");
        if (nick != null) {
            if (nick.length() <= 12) {
                if (token != null) {
                    User user = userRepository.findByUserToken(token);
                    if (user != null) {
                        user.setUserNickname(nick);
                        userRepository.save(user);
                        return "更新成功";
                    } else {
                        return "用户不存在";
                    }
                } else {
                    return "用户未登录";
                }
            } else {
                return "格式错误1-12";
            }
        }
        return "输入有误";
    }

    @GetMapping("/up")
    @ResponseBody
    public String up(HttpServletRequest httpServletRequest,
                     @RequestParam(value = "up") String up) {
        String token = SevenUtil.getCookies(httpServletRequest).get("token");
        if (up != null) {
            if (up.length() <= 300) {
                if (token != null) {
                    User user = userRepository.findByUserToken(token);
                    if (user != null) {
                        user.setUserSignature(up);
                        userRepository.save(user);
                        return "更新成功";
                    } else {
                        return "用户不存在";
                    }
                } else {
                    return "用户未登录";
                }
            } else {
                return "格式错误1-300";
            }
        }
        return "输入有误";
    }


    @GetMapping("/pas")
    @ResponseBody
    public String pas(HttpServletRequest httpServletRequest,
                      @RequestParam(value = "pas") String pas) {
        String token = SevenUtil.getCookies(httpServletRequest).get("token");
        if (pas != null) {
            if (SevenUtil.regex(SevenUtil.regexPassword, pas)) {
                if (token != null) {
                    User user = userRepository.findByUserToken(token);
                    if (user != null) {
                        user.setUserPassword(pas);
                        userRepository.save(user);
                        return "更新成功";
                    } else {
                        return "用户不存在";
                    }
                } else {
                    return "用户未登录";
                }
            } else {
                return "格式错误8-20";
            }
        }
        return "输入有误";
    }

}