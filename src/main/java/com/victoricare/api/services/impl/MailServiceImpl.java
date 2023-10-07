package com.victoricare.api.services.impl;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.victoricare.api.exceptions.MailException;
import com.victoricare.api.models.MailModel;
import com.victoricare.api.services.IMailService;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements IMailService{
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	@Value("${spring.mail.username}")
	private String from;

	@Value("${association.name}")
	private String associationName;

	@Value("${s3.bucket.url}")
    private String s3BucketUrl;

	@Autowired
	private  JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Autowired
	protected MailModel details;

    @Override
	public void sendHtmlMessage() {
        try {
	    	MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
	        Context context = new Context();
	        details.getProperties().put("frontEndUrl", s3BucketUrl);
	        context.setVariables(details.getProperties());
	        message.setFrom(new InternetAddress(this.from, this.associationName));
	        helper.setTo(this.toArray());
	        helper.setSubject(details.getSubject());
	        String html = templateEngine.process(details.getTemplate(), context);
	        helper.setText(html, true);

	        logger.info("Sending email: {} with html body: {}", details, html);
	        javaMailSender.send(message);
        }
        catch(Exception e) {
        	logger.error("Error while sending mail : {}", e);
        	throw new MailException();
        }
    }

    public String[] toArray() {
    	List<String> toList = details.getTo();
    	String[] toArray = new String[toList.size()];

    	for(int i = 0; i < toList.size(); i++) {
    		toArray[i] = toList.get(i);
    	}
       return toArray;
    }
}
