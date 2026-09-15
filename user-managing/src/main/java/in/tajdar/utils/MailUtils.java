package in.tajdar.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class MailUtils {
	// TODO wirte sendMail()
	@Autowired
	private JavaMailSender mailSender;

	private Logger logger = LoggerFactory.getLogger(MailUtils.class);

	public boolean sendMail(String to, String subject, String body) {
		boolean isMailSent = false;
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			mailSender.send(message);
			isMailSent = true;
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("Exception", e);
			return isMailSent;
		}
		return isMailSent;
	}
}
