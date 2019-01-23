package tj.ebm.Mail;

public interface EmailSender {

    void sendEmail(String to, String subject, String content);

}
