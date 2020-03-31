package com.qifen.service;

import com.qifen.Result;
import com.qifen.SevenUtil;
import com.qifen.model.Picture;
import com.qifen.model.User;
import com.qifen.repository.PictureRepository;
import com.qifen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PictureService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    PictureRepository pictureRepository;
    @Value("${duck.path}")
    String path;
    private Integer maxWidth = 0, maxHeight = 0;
    private List<String> fileNameList = new ArrayList<>();

    public String upload(String token, String say, String pictureFlag, HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> multipartFiles = multipartRequest.getFiles("file");
        for (MultipartFile mf : multipartFiles) {
            if (mf.getOriginalFilename() != null) {
                if (!SevenUtil.isImage(mf.getOriginalFilename())) {
                    return "不支持的图片类型或未上传图片" + mf.getOriginalFilename();
                }
                if (mf.getSize() > 10485760) {
                    return "单个图片最大10M";
                }
                try {
                    BufferedImage bufferedImage = ImageIO.read(mf.getInputStream());
                    if (bufferedImage.getWidth() > maxWidth && bufferedImage.getHeight() > maxHeight) {
                        maxWidth = bufferedImage.getWidth();
                        maxHeight = bufferedImage.getHeight();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (multipartFiles.size() < 10) {
            if (say != null && !"".equals(say) && say.length() <= 300) {
                if (pictureFlag != null && !"".equals(pictureFlag) && pictureFlag.length() <= 30) {

                    try {
                        for (MultipartFile mf : multipartFiles) {
                            if (!"".equals(mf.toString()) && mf.getSize() > 0) {
                                String fileName = mf.getOriginalFilename();
                                if (fileName != null) {
                                    fileName = SevenUtil.getUUID() + fileName.substring(fileName.lastIndexOf("."));
                                }
                                File saveFileName = new File(path + "/pic/" + fileName);
                                mf.transferTo(saveFileName);
                                fileNameList.add(fileName);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Picture picture = new Picture();
                    User user = userRepository.findByUserToken(token);
                    picture.setPictureOwner(user.getUserId());
                    picture.setPictureTime(SevenUtil.getTime());
                    picture.setPictureFlag(pictureFlag);
                    picture.setPictureSay(say);
                    picture.setPictureWidth(maxWidth);
                    picture.setPictureHeight(maxHeight);
                    for (int i = 1; i <= fileNameList.size(); i++) {
                        switch (i) {
                            case 1:
                                picture.setPictureOne(fileNameList.get(0));
                                break;
                            case 2:
                                picture.setPictureTwo(fileNameList.get(1));
                                break;
                            case 3:
                                picture.setPictureThree(fileNameList.get(2));
                                break;
                            case 4:
                                picture.setPictureFour(fileNameList.get(3));
                                break;
                            case 5:
                                picture.setPictureFive(fileNameList.get(4));
                                break;
                            case 6:
                                picture.setPictureSix(fileNameList.get(5));
                                break;
                            case 7:
                                picture.setPictureSeven(fileNameList.get(6));
                                break;
                            case 8:
                                picture.setPictureEight(fileNameList.get(7));
                                break;
                            case 9:
                                picture.setPictureNine(fileNameList.get(8));
                                break;
                            default:
                                break;
                        }
                    }
                    pictureRepository.save(picture);
                    multipartFiles.clear();
                    fileNameList.clear();
                    maxHeight = 0;
                    maxWidth = 0;
                    return "上传成功";
                } else {
                    return "标签长度1-30字符";
                }
            } else {
                return "说说长度1-300字符";
            }
        } else {
            return "上传不能大于9个文件";
        }
    }

}
