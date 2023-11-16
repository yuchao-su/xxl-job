package com.xxl.job.executor.util;

import com.alibaba.fastjson2.JSONObject;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.executor.model.SendMailReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@Slf4j
public class EMailSender {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(SendMailReq req) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("hellosuyuchao@qq.com", req.getPersonal());
        helper.setTo(req.getSendTo());
        helper.setSubject(req.getSubject());
        helper.setText(req.getContent(), true);
        mailSender.send(mimeMessage);
        log.info("发送邮件完成。。 req={}", JSONObject.toJSONString(req));
        XxlJobHelper.log("EMailSender sendMail finish. req={}", JSONObject.toJSONString(req));
    }

}
