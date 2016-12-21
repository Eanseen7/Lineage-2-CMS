package studio.lineage2.cms.controller.enter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.other.ValidationResult;
import studio.lineage2.cms.service.MAccountService;
import studio.lineage2.cms.service.ReCaptchaService;
import studio.lineage2.cms.utils.MailUtil;
import studio.lineage2.cms.utils.Rnd;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 Eanseen
 30.05.2016
 */
@Controller @RequestMapping("/enter/recover") public class RecoverController
{
	@Autowired private MailUtil mailUtil;
	@Autowired private MAccountService mAccountService;
	@Autowired private ReCaptchaService reCaptchaService;
	@Autowired private HttpServletRequest request;

	@RequestMapping(method = {RequestMethod.GET})
	public String index(ModelMap model)
	{
		model.addAttribute("mAccount", new MAccount());
		model.addAttribute("page", "cp/enter/recover.vm");
		return "cp/index";
	}

	@RequestMapping(method = {RequestMethod.POST})
	public String recover(ModelMap model, @ModelAttribute(value = "mAccount") @Valid MAccount mAccount, BindingResult result)
	{
		ValidationResult validationResult = reCaptchaService.check(request.getParameter("g-recaptcha-response"), request.getRemoteAddr());
		if(!validationResult.isSuccess())
		{
			model.addAttribute("recaptchaError", "Пройдите проверку");
			model.addAttribute("mAccount", mAccount);
			model.addAttribute("page", "cp/enter/recover.vm");
			return "cp/index";
		}

		if(result.hasErrors())
		{
			model.addAttribute("page", "cp/enter/recover.vm");
			return "cp/index";
		}

		if(!mAccountService.containsByUsername(mAccount.getUsername()))
		{
			result.rejectValue("username", "mAccount.username", "Пользователь не существует");
			model.addAttribute("page", "cp/enter/recover.vm");
			return "cp/index";
		}

		if(mailUtil.isEnabled())
		{
			String password = Rnd.getPassword();

			MAccount temp = mAccountService.findByUsername(mAccount.getUsername());
			temp.setPassword(new BCryptPasswordEncoder().encode(password));
			mAccountService.save(temp);

			StringBuilder sb = new StringBuilder();
			sb.append("Вы восстановили пароль на проекте").append(" ").append(mailUtil.getTitle()).append("<br><br>");
			sb.append("<b>").append("Ваш Email").append(":</b> ").append(mAccount.getUsername()).append("<br>");
			sb.append("<b>").append("Ваш Пароль").append(":</b> ").append(password).append("<br>");
			mailUtil.send(temp.getUsername(), mailUtil.getTitle() + " - Восстановление пароля", sb.toString());

			model.addAttribute("message", "Восстановление пароля прошло успешно, информация отправлена на Email");
		}
		else
		{
			model.addAttribute("message", "Восстановление пароля отключено");
		}

		model.addAttribute("page", "cp/message.vm");
		return "cp/index";
	}
}