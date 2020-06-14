package com.qifen.controller;

import com.qifen.SevenUtil;
import com.qifen.model.Picture;
import com.qifen.model.User;
import com.qifen.repository.PictureRepository;
import com.qifen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 主页的controller，映射index.html
 */

@Controller
public class IndexController {
    @Autowired
    PictureRepository pictureRepository;
    @Autowired
    UserRepository userRepository;
    @Value("${duck.path}")
    public String path;

    private Sort.Order sort;
    private Pageable pageable;
    private Page<Picture> pageList;

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest,
                        Model model,
                        @RequestParam(value = "go", defaultValue = "rec") String go,
                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "size", defaultValue = "12") Integer size) {
        String token = SevenUtil.getCookies(httpServletRequest).get("token");
        if (new File(path + "/tell/tell.txt").exists()) {//公告
            model.addAttribute("tell", SevenUtil.readFile(path + "/tell/tell.txt"));
        }
        model.addAttribute("nick", "登录/注册/找密");
        if (token != null) {//显示登录或者昵称
            User user = userRepository.findByUserToken(token);
            if (user != null) {
                if (user.getUserTokenTime() > SevenUtil.getTime()) {
                    if (user.getUserNickname() != null) {
                        model.addAttribute("nick", user.getUserNickname());
                    } else {
                        model.addAttribute("nick", user.getUserMail());
                    }
                }
            }
        }

        switch (go) {
            case "hot":
                model.addAttribute("go", "/?go=hot");
                sort = new Sort.Order(Sort.Direction.DESC, "pictureView");
                pageable = PageRequest.of(page, size, Sort.by(sort));
                pageList = pictureRepository.findAll(pageable);
                model.addAttribute("pageList", pageList);
                break;
            default:
                model.addAttribute("go", "/");
                model.addAttribute("main", "main");
                sort = new Sort.Order(Sort.Direction.DESC, "pictureTime");
                pageable = PageRequest.of(page, size, Sort.by(sort));
                pageList = pictureRepository.findAll(pageable);
                model.addAttribute("pageList", pageList);
                break;
        }

        return "index";
    }


    @GetMapping("/sea")
    public String find(Model model, HttpServletRequest httpServletRequest,
                       @RequestParam(value = "find") String find,
                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                       @RequestParam(value = "size", defaultValue = "12") Integer size) {
        if (find != null) {
            if (find.length() > 300) return "redirect:/";
            String token = SevenUtil.getCookies(httpServletRequest).get("token");
            model.addAttribute("nick", "登录/注册/找密");
            if (token != null) {//显示登录或者昵称
                if (userRepository.findByUserToken(token).getUserTokenTime() > SevenUtil.getTime()) {
                    User user = userRepository.findByUserToken(token);
                    if (user.getUserNickname() != null) {
                        model.addAttribute("nick", user.getUserNickname());
                    } else {
                        model.addAttribute("nick", user.getUserMail());
                    }
                }
            } else {
                model.addAttribute("nick", "登录/注册/找密");
            }
            model.addAttribute("go", "/sea?find=" + find);
            model.addAttribute("find", find);
            model.addAttribute("search", "search");
            sort = new Sort.Order(Sort.Direction.DESC, "pictureView");
            pageable = PageRequest.of(page, size, Sort.by(sort));
            pageList = pictureRepository.findByPictureSayLike("%" + find + "%", pageable);
            model.addAttribute("pageList", pageList);
            return "index";
        } else {
            return "redirect:/";
        }
    }

}
