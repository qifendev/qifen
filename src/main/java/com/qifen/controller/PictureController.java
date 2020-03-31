package com.qifen.controller;

import com.qifen.SevenUtil;
import com.qifen.model.Picture;
import com.qifen.model.User;
import com.qifen.repository.PictureRepository;
import com.qifen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户在个人界面删除图片后服务端的工作
 */

@Controller
public class PictureController {
    @Autowired
    PictureRepository pictureRepository;
    @Autowired
    UserRepository userRepository;
    @Value("${duck.path}")
    String basePath;

    @GetMapping("/del/{pic}")
    public String del(HttpServletRequest httpServletRequest,
                      @PathVariable(name = "pic") Integer pic,
                      @RequestParam(value = "page", defaultValue = "0") Integer page) {
        String token = SevenUtil.getCookies(httpServletRequest).get("token");
        String srcPath = "/pic/";
        if (token != null && pic != null) {
            User user = userRepository.findByUserToken(token);
            if (user != null) {
                Picture picture = pictureRepository.findByPictureId(pic);
                if (picture != null) {
                    if (picture.getPictureOne() != null)
                        SevenUtil.delFile(basePath, picture.getPictureOne(), srcPath);
                    if (picture.getPictureTwo() != null)
                        SevenUtil.delFile(basePath, picture.getPictureTwo(), srcPath);
                    if (picture.getPictureThree() != null)
                        SevenUtil.delFile(basePath, picture.getPictureThree(), srcPath);
                    if (picture.getPictureFour() != null)
                        SevenUtil.delFile(basePath, picture.getPictureFour(), srcPath);
                    if (picture.getPictureFive() != null)
                        SevenUtil.delFile(basePath, picture.getPictureFive(), srcPath);
                    if (picture.getPictureSix() != null)
                        SevenUtil.delFile(basePath, picture.getPictureSix(), srcPath);
                    if (picture.getPictureSeven() != null)
                        SevenUtil.delFile(basePath, picture.getPictureSeven(), srcPath);
                    if (picture.getPictureEight() != null)
                        SevenUtil.delFile(basePath, picture.getPictureEight(), srcPath);
                    if (picture.getPictureNine() != null)
                        SevenUtil.delFile(basePath, picture.getPictureNine(), srcPath);
                    pictureRepository.delete(picture);
                }
            }
        }
        return "redirect:/my?page=" + page;
    }

}
