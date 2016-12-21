package studio.lineage2.cms.controller.enter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.service.MAccountService;
import studio.lineage2.cms.utils.MailUtil;

/**
 Eanseen
 01.06.2016
 */
@Controller @RequestMapping("/enter/change") public class ChangeController
{
	@Autowired private MailUtil mailUtil;
	@Autowired private MAccountService mAccountService;

	@RequestMapping(method = {RequestMethod.GET})
	public String index(ModelMap model)
	{
		model.addAttribute("page", "cp/enter/change.vm");
		return "cp/index";
	}

	@RequestMapping(method = {RequestMethod.POST})
	public String change(ModelMap model, String password, String repeatPassword)
	{
		if(password.isEmpty() || password.length() < 6)
		{
			model.addAttribute("error", "Пароль должен содержать минимум 6 символов");
			model.addAttribute("page", "cp/enter/change.vm");
			return "cp/index";
		}

		if(!password.equals(repeatPassword))
		{
			model.addAttribute("error", "Пароли не совпадают");
			model.addAttribute("page", "cp/enter/change.vm");
			return "cp/index";
		}

		MAccount mAccount = (MAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		StringBuilder sb = new StringBuilder();
		sb.append("Вы сменили пароль на проекте").append(" ").append(mailUtil.getTitle()).append("<br><br>");
		sb.append("<b>").append("Ваш Email").append(":</b> ").append(mAccount.getUsername()).append("<br>");

		mAccount.setPassword(new BCryptPasswordEncoder().encode(password));
		mAccountService.save(mAccount);

		mailUtil.send(mAccount.getUsername(), mailUtil.getTitle() + " - Смена пароля", sb.toString());

		if(mailUtil.isEnabled())
		{
			model.addAttribute("message", "Смена пароля прошла успешно, информация отправлена на Email");
		}
		else
		{
			model.addAttribute("message", "Смена пароля прошла успешно");
		}

		model.addAttribute("page", "cp/message.vm");
		return "cp/index";
	}
}