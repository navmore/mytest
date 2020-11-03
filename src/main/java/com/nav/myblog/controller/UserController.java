package com.nav.myblog.controller;

import com.nav.myblog.comments.mail.SendMail;
import com.nav.myblog.comments.rtJson.Result;
import com.nav.myblog.comments.rtJson.ResultUtil;
import com.nav.myblog.entity.User;
import com.nav.myblog.service.UserService;
import com.nav.myblog.utils.CommonUtil;
import com.nav.myblog.utils.EncryptUtil;
import com.nav.myblog.utils.PhoneCheck;
import com.nav.myblog.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SendMail sendMail;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
    * 用户注册
    * */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public Result registUserr(@Valid User user, @RequestParam(value = "code") String code, HttpServletRequest request) {
        logger.info("注册中...");
        //判断当前用户信息是否符合格式
        if(code == null || !(CommonUtil.checkCaptcha(request, redisUtils, code)))
            return ResultUtil.error("验证码不正确");
        if(!CommonUtil.isEmail(user.getEmail()))
            return ResultUtil.error("邮箱格式不正确");
        if(!PhoneCheck.isPhoneLegal(user.getPhone()))
            return ResultUtil.error("手机格式不正确");
        User u = new User();
        user.setEmail(user.getEmail());
        User u1 = userService.queryOne(u);
        if(u1 != null)
            return ResultUtil.error("该用户已经注册，不能重复注册");
        user.setIsActive("0");
        user.setIsCanUse("0");
        //密码进行加密
        String s = EncryptUtil.getInstance().SHA1(user.getPassword(), "salt");
        user.setPassword(s);
        user.setHeadPhone("aaa.jpg");
        sendMails(user);
        userService.save(user);
        logger.info("注册完毕....");
        return ResultUtil.data(user, "邮件发送到邮箱请进一步激活");
    }
    /**
    * 邮箱激活
    * */
    @RequestMapping(value = "active", method = RequestMethod.GET)
    public Result mailActive(@RequestParam(name = "code") String code, @RequestParam(name = "email") String email) {
        User user1 = new User();
        user1.setEmail(user1.getEmail());
        User user = userService.queryOne(user1);
        if(user != null && "1".equals(user.getIsActive())) {
            return ResultUtil.success("当前用户已经激活，可以直接登陆");
        }
        String validacode = (String) redisUtils.get(email);
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("email", email);
        if(validacode == null)
            return ResultUtil.error(304, "当前验证信息已经过期，请重新激活");
        if(code.equals(validacode))
            return ResultUtil.error(304,"验证信息和邮箱不匹配");
        user.setIsActive("1");
        userService.update(user);
        logger.info("激活完成");
        return ResultUtil.success("激活完成");
    }

    /**
     * 邮件从新激活
     * */
    @RequestMapping(value = "/resend", method = RequestMethod.GET)
    public Result reMailSend(@RequestParam(name = "email")String email) {
        User user1 = new User();
        user1.setEmail(email);
        User user = userService.queryOne(user1);
        if(user == null) ResultUtil.error(402, "当前邮箱还没有注册");
        if("1".equals(user.getIsActive())) {
            return ResultUtil.success("当前用户已经激活，可以直接登陆");
        }
        sendMails(user);
        return ResultUtil.success("ok");
    }

    //发送邮件
    private void sendMails(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        String validatecode = EncryptUtil.getInstance().MD5(email + password + UUID.randomUUID().toString(), "salt");
        redisUtils.set(user.getEmail(), validatecode, 24 * 60 * 60);
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head>");
        sb.append("<body><h1>该用户激活后才能使用</h1><a href='http://localhost:8080/user/active?code=" + validatecode + "&email=" + user.getEmail() + "'>点击此处激活</a></body></html>");
        sendMail.sendHtmlMail(user.getEmail(), "邮箱激活", sb.toString());
    }
}
