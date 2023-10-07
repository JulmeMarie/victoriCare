package com.victoricare.api.security;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class MailConfig {
	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private Integer port;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean isSmtpAuth;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private boolean starttlsEnable;

	 @Bean
	    public SpringTemplateEngine springTemplateEngine() {
	        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
	        springTemplateEngine.addTemplateResolver(emailTemplateResolver());
	        return springTemplateEngine;
	    }

	    public ClassLoaderTemplateResolver emailTemplateResolver() {
	        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
	        emailTemplateResolver.setPrefix("/templates/mail/html/");
	        emailTemplateResolver.setSuffix(".html");
	        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
	        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
	        emailTemplateResolver.setCacheable(false);
	        return emailTemplateResolver;
	    }


	@Bean
	JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(host);
	    mailSender.setPort(port);
	    mailSender.setUsername(username);
	    mailSender.setPassword(password);

	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", isSmtpAuth);
	    props.put("mail.smtp.starttls.enable", starttlsEnable);
	    props.put("mail.debug", "true");

	    return mailSender;
	}
	/*
    @Bean
     SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.addTemplateResolver(emailTemplateResolver());
        return springTemplateEngine;
    }

    @Bean
     ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("/email-templates/html/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        emailTemplateResolver.setCacheable(false);
        return emailTemplateResolver;
    }

	@Bean
	 SimpleMailMessage templateSimpleMessage() {
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setText(
	      "This is the test email template for your email:\n%s\n");
	    return message;
	}*/
}
