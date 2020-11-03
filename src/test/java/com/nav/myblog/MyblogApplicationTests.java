package com.nav.myblog;

import com.nav.myblog.comments.Constents;
import com.nav.myblog.entity.User;
import com.nav.myblog.service.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyblogApplicationTests {
    //注意Logger是slf4j里面的
    private static final Logger logger = LoggerFactory.getLogger(MyblogApplicationTests.class);
    @Autowired
    private UserService service;
    @Test
    void contextLoads() {
        User user = new User();
        user.setIsActive("1");
        System.out.println(service.queryOne(user));
        logger.info("aaa");
    }

}
