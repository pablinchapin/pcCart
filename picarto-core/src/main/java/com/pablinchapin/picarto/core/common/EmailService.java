/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.picarto.core.common;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 *
 * @author pvargas
 */

@Component
public class EmailService {
    
    private static final Logger logger = Logger.getLogger(EmailService.class);
    
    @Autowired 
    JavaMailSender javaMailSender;
    
    
    @Value("${support.email}")
    String supportEmail;
    
    
    public void sendEmail(String to, String subject, String content){
        
        try{
            
            final MimeMessage  mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            
            message.setSubject(subject);
            message.setFrom(supportEmail);
            message.setTo(to);
            message.setText(content, true);
            
            javaMailSender.send(message.getMimeMessage());
        
        }catch(MailException | MessagingException e){
            
            logger.error(e);
            //throw new JCartException("Unable to send email");
        
        }
    
    }
    
}
