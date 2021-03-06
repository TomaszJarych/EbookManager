package tj.ebm.Mail.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tj.ebm.Mail.EmailSender;

@Service
public class MailSenderImpl implements EmailSender {

    private final JavaMailSender emailSender;

    @Autowired
    public MailSenderImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String content)
            throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        emailSender.send(message);

    }

}
