package com.l4plan.api.rest.config.app;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class AppConfig {

    private static String HOST = "smtp.gmail.com";
    private static int PORT = 587;
    private static String USERNAME = "l4plan.contact@gmail.com";
    private static String PASSWORD = "czozdgmpfbfhbdfq";
    private static String PROPS_TRANSPORT = "mail.transport.protocol";
    private static String TRANSPORT = "smtp";
    private static String PROPS_AUTH = "mail.smtp.auth";
    private static String PROPS_ENABLE =  "mail.smtp.starttls.enable";
    private static String PROPS_DEBUG = "mail.debug";
    private static String TRUE = "true";    

    /** Define Beans here */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(HOST);
        mailSender.setPort(PORT);

        mailSender.setUsername(USERNAME);
        mailSender.setPassword(PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put(PROPS_TRANSPORT, TRANSPORT);
        props.put(PROPS_AUTH, TRUE);
        props.put(PROPS_ENABLE, TRUE);
        props.put(PROPS_DEBUG, TRUE);

        return mailSender;
    }
}
