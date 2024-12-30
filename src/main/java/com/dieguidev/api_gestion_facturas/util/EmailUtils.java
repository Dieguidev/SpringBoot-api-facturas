package com.dieguidev.api_gestion_facturas.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailUtils {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    public void senSimpleMessage(String to, String subject, String text, List<String> list) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        if(list != null && list.size() > 0) {
            message.setCc(getCcArray(list));
        }
        javaMailSender.send(message);
    }

    private String[] getCcArray (List<String> cclist) {
        String[] cc = new String[cclist.size()];
        for (int i = 0; i < cclist.size(); i++) {
            cc[i] = cclist.get(i);
        }
        return cc;
    }
}
