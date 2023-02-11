package cn.lliiooll.autotask.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import cn.lliiooll.autotask.utils.RedisUtil;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class MailService {

    private final JavaMailSender sender;
    private final RedisUtil redisUtil;
    @Value("${autotask.url}")
    private String url;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${autotask.mail.html}")
    private boolean html;
    @Value("${autotask.mail.path}")
    private String htmlPath;
    @Value("${autotask.mail.text}")
    private String text;

    @Autowired
    public MailService(JavaMailSender sender, RedisUtil redisUtil) {
        this.sender = sender;
        this.redisUtil = redisUtil;
    }

    @SneakyThrows
    public void sendVerifyMail(String mail) {
        String path = RandomUtil.randomString(20);
        redisUtil.set("mail_" + path, mail, 3600);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setCc(mail);
        helper.setTo(mail);
        helper.setFrom(from);
        helper.setSubject("AutoTask - 邮箱验证");
        String verifyUrl = url + "/auth/verify/" + path;
        if (html) {
            helper.setText(FileUtil.readString(htmlPath, StandardCharsets.UTF_8).replace("{url}", verifyUrl), true);
        } else {
            helper.setText(text.replace("{url}", verifyUrl));
        }
        sender.send(message);
    }
}
