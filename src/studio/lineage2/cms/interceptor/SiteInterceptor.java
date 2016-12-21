package studio.lineage2.cms.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.service.ForumService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 Eanseen
 18.03.2016
 */
@Component public class SiteInterceptor extends HandlerInterceptorAdapter
{
	@Value("${template}") private String template;
	@Value("${recaptcha.siteKey}") private String recaptchaSiteKey;
	@Value("${forum.link}") private String forumLink;
	@Value("${vote.mmotop.id}") private String mmotopId;
	@Value("${vote.mmotop.enable}") private boolean voteMmotopEnable;
	@Value("${lucky.wheel}") private boolean luckyWheel;
	@Autowired private ForumService forumService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception
	{
		//TODO Ссаный костыль, точно знаю что есть более изящное решение
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.getPrincipal() instanceof MAccount && request.getRequestURI().contains("/enter"))
		{
			if(!request.getRequestURI().contains("change"))
			{
				response.sendRedirect("/cp");
				return;
			}
		}

		if(model == null)
		{
			return;
		}

		model.addObject("template", "/static/" + template);
		model.addObject("recaptchaSiteKey", recaptchaSiteKey);
		model.addObject("forumLink", forumLink);
		model.addObject("forumType", forumService.getForumType());
		model.addObject("forumThemes", forumService.getThemes());
		model.addObject("mmotopId", mmotopId);

		model.addObject("voteMmotopEnable", voteMmotopEnable);
		model.addObject("luckyWheel", luckyWheel);

		authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null)
		{
			Object o = authentication.getPrincipal();
			if(o instanceof MAccount)
			{
				MAccount mAccount = (MAccount) o;
				model.addObject("mAccount", mAccount);
			}
		}
	}
}