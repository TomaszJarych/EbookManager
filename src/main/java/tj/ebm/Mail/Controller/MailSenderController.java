package tj.ebm.Mail.Controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tj.ebm.Commons.ErrorsUtil.ErrorsUtil;
import tj.ebm.Commons.Result.Result;
import tj.ebm.Commons.SessionStorageData.SessionStorageData;
import tj.ebm.Mail.EmailSender;
import tj.ebm.Mail.Domain.Mail;

@RestController
@RequestMapping(path = "/message")
public class MailSenderController {

	private final EmailSender emailSender;
	private final SessionStorageData storageData;

	@Autowired
	public MailSenderController(EmailSender emailSender,
			SessionStorageData storageData) {
		this.emailSender = emailSender;
		this.storageData = storageData;
	}

	@PostMapping(path = "/sendMail", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
	public Result sendEmail(@Valid @RequestBody Mail mail,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
					bindingResult.getFieldErrors()), mail);
		}
		if (storageData.getLoggedUser().getEmail() == null
				|| storageData.getLoggedUser().getEmail() == "") {
			return Result.error("You're not logged. Please logg in");
		}
		String content = storageData.getLoggedUser().getEmail()
				+ " have sent you messeage: \n " + mail.getContent();
		emailSender.sendEmail(mail.getReciever(), mail.getSubject(), content);

		return Result.ok("Mail has been sent");
	}
}
