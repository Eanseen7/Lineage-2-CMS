package studio.lineage2.cms.controller.enter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller @RequestMapping("/enter") public class AuthController
{
	@Autowired private ReCaptchaService reCaptchaService;
	@Autowired private HttpServletRequest request;
	@Autowired private MAccountService mAccountService;
	@Autowired private AuthenticationManager authenticationManager;

	@RequestMapping(value = "/auth", method = {RequestMethod.GET})
	public String index(ModelMap model)
	{
		model.addAttribute("mAccount", new MAccount());
		model.addAttribute("page", "cp/enter/auth.vm");
		return "cp/index";
	}

	@RequestMapping(value = "/auth", method = {RequestMethod.POST})
	public String post(ModelMap model, @ModelAttribute(value = "mAccount") @Valid MAccount mAccount, BindingResult result)
	{
		ValidationResult validationResult = reCaptchaService.check(request.getParameter("g-recaptcha-response"), request.getRemoteAddr());
		if(!validationResult.isSuccess())
		{
			model.addAttribute("mAccount", mAccount);
			model.addAttribute("recaptchaError", "Пройдите проверку");
			model.addAttribute("page", "cp/enter/auth.vm");
			return "cp/index";
		}

		if(result.hasErrors())
		{
			model.addAttribute("mAccount", mAccount);
			model.addAttribute("page", "cp/enter/auth.vm");
			return "cp/index";
		}

		if(!mAccountService.containsByUsername(mAccount.getUsername()))
		{
			result.rejectValue("username", "mAccount.username", "Аккаунт не найден");
			model.addAttribute("mAccount", mAccount);
			model.addAttribute("page", "cp/enter/auth.vm");
			return "cp/index";
		}

		MAccount temp = mAccountService.findByUsername(mAccount.getUsername());

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(!encoder.matches(mAccount.getPassword(), temp.getPassword()))
		{
			result.rejectValue("password", "mAccount.password", "Неверный пароль");
			model.addAttribute("mAccount", mAccount);
			model.addAttribute("page", "cp/enter/auth.vm");
			return "cp/index";
		}

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(mAccount.getUsername(), mAccount.getPassword());
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.addAttribute("page", "cp/enter/auth.vm");
		return "cp/index";
	}
}