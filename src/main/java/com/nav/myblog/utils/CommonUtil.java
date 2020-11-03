package com.nav.myblog.utils;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
      /* 生成验证码图片
     * @param request 设置session
     * @param response 转成图片
     * @param captchaProducer 生成图片方法类
     * @throws Exception
     */
    public static final String LOGIN_VALIDATE_CODE = "login_validate_code";

    public static void validateCode(HttpServletRequest request, HttpServletResponse response, DefaultKaptcha captchaProducer, RedisUtils redisUtils) throws Exception{
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = captchaProducer.createText();

        // store the text in the session
      //  request.getSession().setAttribute(LOGIN_VALIDATE_CODE, capText);

        redisUtils.set(RealIp.getIPAddress(request), capText, 1 * 60000);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);

        ServletOutputStream out = response.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
    /**
    * 检测验证码是否正确
    */
    public static boolean checkCaptcha(HttpServletRequest request, RedisUtils redisUtils, String code) {
        String num = (String) redisUtils.get(RealIp.getIPAddress(request));
        if(num == null) return false;
        if(num.equals(code)) return true;
        return false;
    }

    /**
    * 判断邮箱是否合法
    * */
    public static boolean isEmail(String email) {
        if (email == null) return false;
        String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(email);
        if(m.matches()) return true;
        return false;
    }
}
