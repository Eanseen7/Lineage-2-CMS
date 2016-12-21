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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 Eanseen
 30.05.2016
 */
@Controller @RequestMapping("/enter") public class RegController
{
	@Autowired private MailUtil mailUtil;
	@Autowired private MAccountService mAccountService;
	@Autowired private ReCaptchaService reCaptchaService;
	@Autowired private HttpServletRequest request;

	@RequestMapping(value = "/reg", method = {RequestMethod.GET})
	public String index(ModelMap model)
	{
		model.addAttribute("mAccount", new MAccount());
		model.addAttribute("page", "cp/enter/reg.vm");
		return "cp/index";
	}

	@RequestMapping(value = "/reg", method = {RequestMethod.POST})
	public String reg(ModelMap model, @ModelAttribute(value = "mAccount") @Valid MAccount mAccount, BindingResult result)
	{
		ValidationResult validationResult = reCaptchaService.check(request.getParameter("g-recaptcha-response"), request.getRemoteAddr());
		if(!validationResult.isSuccess())
		{
			model.addAttribute("recaptchaError", "Пройдите проверку");
			model.addAttribute("mAccount", mAccount);
			model.addAttribute("page", "cp/enter/reg.vm");
			return "cp/index";
		}

		if(result.hasErrors())
		{
			model.addAttribute("page", "cp/enter/reg.vm");
			return "cp/index";
		}

		if(mAccountService.containsByUsername(mAccount.getUsername()))
		{
			result.rejectValue("username", "mAccount.username", "Такой пользователь уже существует");
			model.addAttribute("page", "cp/enter/reg.vm");
			return "cp/index";
		}

		if(!mAccount.getPassword().equals(mAccount.getRepeatPassword()))
		{
			result.rejectValue("repeatPassword", "mAccount.repeatPassword", "Пароли не совпадают");
			model.addAttribute("page", "cp/enter/reg.vm");
			return "cp/index";
		}

		mAccount.setUsername(mAccount.getUsername().toLowerCase());
		mAccount.setPassword(new BCryptPasswordEncoder().encode(mAccount.getPassword()));
		mAccount.setAdmin(false);
		mAccountService.save(mAccount);

		if(mailUtil.isEnabled())
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Спасибо за регистрацию на проекте").append(" ").append(mailUtil.getTitle()).append("<br><br>");
			sb.append("<b>").append("Ваш Email").append(":</b> ").append(mAccount.getUsername()).append("<br>");
			mailUtil.send(mAccount.getUsername(), mailUtil.getTitle() + " - Регистрация", sb.toString());

			model.addAttribute("message", "Регистрация прошла успешно, информация отправлена на Email");
		}
		else
		{
			model.addAttribute("message", "Регистрация прошла успешно");
		}

		model.addAttribute("mAccount", null);
		model.addAttribute("page", "cp/message.vm");
		return "cp/index";
	}
}