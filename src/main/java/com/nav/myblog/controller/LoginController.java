package com.nav.myblog.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.nav.myblog.comments.Constents;
import com.nav.myblog.comments.rtJson.Result;
import com.nav.myblog.comments.rtJson.ResultUtil;
import com.nav.myblog.entity.User;
import com.nav.myblog.service.UserService;
import com.nav.myblog.utils.CommonUtil;
import com.nav.myblog.utils.EncryptUtil;
import com.nav.myblog.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final Logger loggerr = LoggerFactory.getLogger(LoginController.class);
    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/getVerify", method = RequestMethod.GET)
    public void getVerify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CommonUtil.validateCode(request, response, defaultKaptcha, redisUtils);
    }

    /**
     * 用户登陆
     * */
    @RequestMapping(method = RequestMethod.POST)
    public Result checkRequest(@RequestParam String email, @RequestParam String password, @RequestParam String code, HttpSession session) {
        User user1 = new User();
        user1.setEmail(email);
        //根据邮箱查询用户
        User user =  userService.queryOne(user1);
        String pass = EncryptUtil.getInstance().SHA1(password, "salt");
        if(!pass.equals(user.getPassword()))
            return ResultUtil.error("密码不正确");
        if("0".equals(user.getIsActive()))
            return ResultUtil.error("该用户还没有激活，请先激活");
        session.setAttribute("user", user);
        for(int i = 0 ; i < 3; i++) {
            rabbitTemplate.convertAndSend(Constents.EXCHANGE_TOPIC, "mail.send", String.valueOf(i) + "mail.send");
            rabbitTemplate.convertAndSend(Constents.EXCHANGE_TOPIC, "mail", String.valueOf(i) + "mail");
        }
        return ResultUtil.data(user, "用户登陆成功");
    }


}
