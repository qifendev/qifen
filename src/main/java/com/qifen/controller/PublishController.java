package com.qifen.controller;

import com.qifen.SevenUtil;
import com.qifen.repository.UserRepository;
import com.qifen.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 映射pub.html,上传图片
 */

@Controller
public class PublishController {
    @Autowired
    PictureService pictureService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/upload")
    public String upload(@RequestParam(name = "say") String say,
                         @RequestParam(name = "flag") String flag,
                         HttpServletRequest httpServletRequest, Model model) {
        String token = SevenUtil.getCookies(httpServletRequest).get("token");
        String info = pictureService.upload(token, say, flag, httpServletRequest);
        if (info.equals("上传成功")) {
            return "redirect:/";
        }
        model.addAttribute("info", info);
        return "pub";

    }

    @GetMapping("/pub")
    public String publish(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String token = SevenUtil.getCookies(httpServletRequest).get("token");
        if (!userRepository.existsUserByUserToken(token)) {
            SevenUtil.clearAllCookies(httpServletRequest, httpServletResponse);
            return "redirect:/";
        }
        return "pub";
    }
}
