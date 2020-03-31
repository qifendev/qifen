package com.qifen.controller;

import com.qifen.SevenUtil;
import com.qifen.model.Picture;
import com.qifen.model.User;
import com.qifen.model.Viewed;
import com.qifen.repository.PictureRepository;
import com.qifen.repository.UserRepository;
import com.qifen.repository.ViewedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 映射view.html界面，查看图片操作
 */

@Controller
public class ViewController {
    @Autowired
    PictureRepository pictureRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ViewedRepository viewedRepository;


    @GetMapping("/view/{pic}")
    public String view(@PathVariable(name = "pic") Integer pic, Model model) {
        if (pic != null) {
            Picture picture = pictureRepository.findByPictureId(pic);
            if (picture != null) {
                User user = userRepository.findByUserId(picture.getPictureOwner());
                if (user != null) {
                    if (!viewedRepository.existsByViewedUserIdAndViewedPictureId(user.getUserId(), picture.getPictureId())) {
                        Viewed viewed = new Viewed();
                        viewed.setViewedPictureId(picture.getPictureId());
                        viewed.setViewedUserId(user.getUserId());
                        viewed.setViewedTime(SevenUtil.getTime());
                        viewedRepository.save(viewed);
                        picture.setPictureView(picture.getPictureView() + 1);
                        pictureRepository.save(picture);
                    }
                    model.addAttribute("user", user);
                }
                model.addAttribute("pic", picture);
            } else {
                return "redirect:/";
            }
        }
        return "view";
    }

}
