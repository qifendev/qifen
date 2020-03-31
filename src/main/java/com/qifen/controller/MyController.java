package com.qifen.controller;

import com.qifen.SevenUtil;
import com.qifen.model.Picture;
import com.qifen.model.User;
import com.qifen.repository.PictureRepository;
import com.qifen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 我的界面映射my.html
 */

@Controller
public class MyController {
    @Autowired
    PictureRepository pictureRepository;
    @Autowired
    UserService userService;

    @GetMapping("my")
    public String my(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                     Model model,
                     @RequestParam(value = "page", defaultValue = "0") Integer page,
                     @RequestParam(value = "size", defaultValue = "12") Integer size) {
        User user = userService.tokenToUser(httpServletRequest);
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("go", "/my");
            Sort.Order sort = new Sort.Order(Sort.Direction.DESC, "pictureTime");
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            Page<Picture> pageList = pictureRepository.findByPictureOwner(user.getUserId(), pageable);
            model.addAttribute("pageList", pageList);
        } else {
            SevenUtil.clearAllCookies(httpServletRequest, httpServletResponse);
            return "redirect:/";
        }
        return "my";
    }
}
