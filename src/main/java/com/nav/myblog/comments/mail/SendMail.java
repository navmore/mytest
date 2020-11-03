package com.nav.myblog.comments.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Component
public class SendMail {
    private static final Logger logger = LoggerFactory.getLogger(SendMail.class);
    @Autowired
    private JavaMailSenderImpl mailSender;

    private static final String SENDER = "navmore@163.com";
    /*
    * 发送包含简单文本的邮件
    * */
    @Async
    public void SendTxtMail(String toAddress, String title, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setFrom(SENDER);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(text);
        try {
            mailSender.send(simpleMailMessage);
            System.out.println("发送邮件成功");
        } catch (MailException e) {
            logger.error("发送邮件错误", e);
        }
    }
    /**
     * 发送包含HTML文本的邮件
     * @throws Exception
     */
    @Async
    public void sendHtmlMail(String toAddress, String title, String txt){
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setFrom(SENDER);
            mimeMessageHelper.setSubject(title);
            //设置html格式
            mimeMessageHelper.setText(txt, true);
            System.out.println("邮件发送完毕");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("发送邮件错误", e);
        }
    }
    /**
     * 发送包含内嵌图片的邮件
     * @throws Exception
     */
    @Async
    public void sendAttachedImageMail(String toAddress, String title, String txt, String path) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // multipart模式
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setFrom(SENDER);
        mimeMessageHelper.setSubject(title);

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head>");
        sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p>");
        // cid为固定写法，imageId指定一个标识
        sb.append("<img src=\"cid:imageId\"/></body>");
        sb.append("</html>");

        // 启用html
        mimeMessageHelper.setText(sb.toString(), true);

        // 设置imageId
        FileSystemResource img = new FileSystemResource(path);
        mimeMessageHelper.addInline("imageId", img);

        // 发送邮件
        mailSender.send(mimeMessage);

        System.out.println("邮件已发送");
    }
    /**
     * 发送包含附件的邮件
     * @throws Exception
     */
    @Async
    public void sendMimeMessge(String to, String subject, String content, Map<String, String> rscIdMap) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(SENDER);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            for (Map.Entry<String, String> entry : rscIdMap.entrySet()) {
                FileSystemResource file = new FileSystemResource(new File(entry.getValue()));
                helper.addInline(entry.getKey(), file);
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送带静态文件的MimeMessge时发生异常！", e);
        }
    }
    //public void sendAttachedImageMail() throws Exception {
    //    MimeMessage mimeMessage = mailSender.createMimeMessage();
    //    // multipart模式
    //    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    //    mimeMessageHelper.setTo("lianggzone@163.com");
    //    mimeMessageHelper.setFrom("lianglevel@163.com");
    //    mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【图片】");
    //
    //    StringBuilder sb = new StringBuilder();
    //    sb.append("<html><head></head>");
    //    sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p>");
    //    // cid为固定写法，imageId指定一个标识
    //    sb.append("<img src=\"cid:imageId\"/></body>");
    //    sb.append("</html>");
    //
    //    // 启用html
    //    mimeMessageHelper.setText(sb.toString(), true);
    //
    //    // 设置imageId
    //    FileSystemResource img = new FileSystemResource(new File("D:/images/01.jpg"));
    //    mimeMessageHelper.addInline("imageId", img);
    //
    //    // 发送邮件
    //    mailSender.send(mimeMessage);
    //
    //    System.out.println("邮件已发送");
    //}
}
