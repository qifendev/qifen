package com.qifen;

import com.qifen.model.Picture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供各种小工具的类
 */
@Component
public class SevenUtil {
    public static String regexPassword = "[a-zA-Z0-9]{8,20}";
    public static String regexMail = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    //    public static String uploadPath = "D://ideawebprojects//qifen//src//main//resources//static//picture";


    //删除单个文件
    public static void delFile(String basePath, String picName, String srcPath) {
        //relPath路径案例:D://duck//pic//xxx.png
        String relPath = basePath + srcPath + picName;
        File file = new File(relPath);
        if (file.exists()) {
            file.delete();
        }
    }


    //获取uuid
    public static String getUUID() {
        String uuid;
        uuid = UUID.randomUUID().toString();
        return uuid;
    }


    //获取时间戳
    public static long getTime() {
        long time;
        time = System.currentTimeMillis();
        return time;
    }


    //正则表达式匹配
    public static boolean regex(String regex, String pair) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pair);
        return m.matches();
    }

    public static boolean isImage(String fileName) {
        int i = fileName.lastIndexOf('.');
        String fileType = fileName.substring(i + 1);
        if ("JPG".equals(fileType) || "jpg".equals(fileType) ||
                "png".equals(fileType) || "gif".equals(fileType) ||
                "tif".equals(fileType) || "bmp".equals(fileType) ||
                "jpeg".equals(fileType)) {
            return true;
        }
        return false;
    }

    //获取99999-100000的随机数
    public static Integer getCode() {
        return new Random().nextInt(899999) + 100000;
    }

    public static String readFile(String filePath) {
        String allText = "";
        String line;
        try (FileReader reader = new FileReader(filePath);
             BufferedReader br = new BufferedReader(reader)
        ) {
            while ((line = br.readLine()) != null) {
                allText += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allText;
    }

    public static void writeFile(String filePath, String writeText) {
        try {
            File writeName = new File(filePath);
            writeName.createNewFile();
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(writeText); // \r\n即为换行
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void deleteDir(File file) {
        File[] f = file.listFiles();
        if (f != null) {
            for (File value : f) {
                if (value.isDirectory()) {
                    deleteDir(value);
                }
                value.delete();
            }
        }

    }


    //清除所有Cookie
    public static void clearAllCookies(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = getCookies(request);
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> me = iter.next();
            Cookie cookie = new Cookie(me.getKey(), "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    //设置token
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String key, String value, int expiry) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }

    //获取所有Cookie
    public static Map<String, String> getCookies(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (!"JSESSION".equals(cookie.getName()))
                    map.put(cookie.getName(), cookie.getValue());
            }
        }
        return map;
    }

    public static Date millisToDate(long time) {
        Date date = new Date();
        date.setTime(time);
        return date;
    }


}
