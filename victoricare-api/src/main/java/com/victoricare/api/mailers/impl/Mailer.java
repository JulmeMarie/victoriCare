package com.victoricare.api.mailers.impl;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.InternalServerException;
import com.victoricare.api.mailers.IMailer;
import com.victoricare.api.mailers.Maill;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Mailer implements IMailer {

	@Value("${spring.mail.username}")
	private String from;

	@Value("${association.name}")
	private String associationName;

	@Value("${s3.bucket.url}")
	private String s3BucketUrl;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Autowired
	protected Maill mail;

	@Override
	public void sendHtmlMessage() {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Context context = new Context();
			mail.getProperties().put("frontEndURL", s3BucketUrl);
			context.setVariables(mail.getProperties());
			message.setFrom(new InternetAddress(this.from, this.associationName));
			helper.setTo(mail.getTo().toArray(new String[0]));
			helper.setSubject(mail.getSubject());
			String html = templateEngine.process(mail.getTemplate(), context);
			helper.setText(html, true);

			log.info("Sending email: {} with html body: {}", mail, html);
			javaMailSender.send(message);
		} catch (Exception e) {
			log.error("Error while sending mail : {}", e);
			throw new InternalServerException(EMessage.MAIL_NOT_SENT);
		}
	}
}
