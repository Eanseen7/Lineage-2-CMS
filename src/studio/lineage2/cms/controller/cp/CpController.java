package studio.lineage2.cms.controller.cp;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.lineage2.cms.model.GAccount;
import studio.lineage2.cms.model.IMessage;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.model.Server;
import studio.lineage2.cms.model.ServerType;
import studio.lineage2.cms.service.ForumService;
import studio.lineage2.cms.service.GAccountService;
import studio.lineage2.cms.service.ItemService;
import studio.lineage2.cms.service.MAccountService;
import studio.lineage2.cms.service.ServerService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 Eanseen
 23.10.2015
 */
@Controller @RequestMapping("/cp") public class CpController
{
	@Autowired private ServerService serverService;
	@Autowired private MAccountService mAccountService;
	@Autowired private GAccountService gAccountService;
	@Autowired private ForumService forumService;
	@Autowired private ItemService itemService;

	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String index(ModelMap model)
	{
		List<Server> servers = new ArrayList<>();
		serverService.findAll().stream().filter(server->server.getType() == ServerType.LOGIN && server.isEnable()).forEach(servers::add);
		model.addAttribute("servers", servers);

		model.addAttribute("gAccountService", gAccountService);

		model.addAttribute("forumService", forumService);
		model.addAttribute("itemService", itemService);

		model.addAttribute("page", "cp/user/index.vm");
		return "cp/index";
	}

	@RequestMapping(value = "/exit", method = {RequestMethod.GET})
	public void exit(HttpServletResponse httpServletResponse)
	{
		SecurityContextHolder.clearContext();
		try
		{
			httpServletResponse.sendRedirect("/enter");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/changeGAccountPassword/{serverId}/{account}", method = {RequestMethod.GET})
	public String changePassword(ModelMap model, HttpServletResponse httpServletResponse, @PathVariable String serverId, @PathVariable String account)
	{
		if(!StringUtils.isNumeric(serverId))
		{
			try
			{
				httpServletResponse.sendRedirect("/cp");
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			return null;
		}

		Server server = serverService.findOne(Long.parseLong(serverId));
		if(server == null || !server.isEnable())
		{
			try
			{
				httpServletResponse.sendRedirect("/cp");
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			return null;
		}

		MAccount mAccount = (MAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<GAccount> gAccounts = gAccountService.findByMAccountIdAndServerId(mAccount.getId(), Long.parseLong(serverId));
		if(gAccounts.isEmpty())
		{
			try
			{
				httpServletResponse.sendRedirect("/cp");
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			return null;
		}

		for(GAccount gAccount : gAccounts)
		{
			if(gAccount.getName().equals(account))
			{
				model.addAttribute("server", server);
				model.addAttribute("account", account);
				model.addAttribute("page", "cp/user/changeGAccountPassword.vm");
				return "cp/index";
			}
		}

		try
		{
			httpServletResponse.sendRedirect("/cp");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/setVoteName", method = {RequestMethod.POST})
	public
	@ResponseBody
	IMessage setVoteName(String voteName)
	{
		if(!voteName.matches("[A-Za-z0-9]{6,16}"))
		{
			return new IMessage(IMessage.Type.FAIL, "Идентификатор должен содержать 6-16 символов (A-Za-z0-9)");
		}

		if(mAccountService.containsByVoteName(voteName))
		{
			return new IMessage(IMessage.Type.FAIL, "Идентификатор уже занят");
		}

		MAccount mAccount = (MAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mAccount.setVoteName(voteName);
		mAccountService.save(mAccount);

		return new IMessage(IMessage.Type.SUCCESS);
	}
}