package studio.lineage2.cms.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import studio.lineage2.cms.model.Server;
import studio.lineage2.cms.model.ServerType;
import studio.lineage2.cms.service.ServerService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 Eanseen
 07.06.2016
 */
@Controller @RequestMapping("/admin/server") public class AdminServerController
{
	@Autowired private ServerService serverService;

	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String index(ModelMap model)
	{
		Map<Long, String> loginServers = new TreeMap<>();
		for(Server server : serverService.findAll())
		{
			if(server.getType() == ServerType.LOGIN)
			{
				loginServers.put(server.getId(), server.getName());
			}
		}
		model.addAttribute("loginServers", loginServers);

		model.addAttribute("types", ServerType.getMap());
		model.addAttribute("server", new Server());
		model.addAttribute("servers", serverService.findAll());

		AdminController.addAttributes(model, "cp/admin/server/index.vm");

		return "cp/index";
	}

	@RequestMapping(value = "add", method = {RequestMethod.POST})
	public void add(@ModelAttribute Server server, HttpServletResponse httpServletResponse)
	{
		serverService.save(server);
		try
		{
			httpServletResponse.sendRedirect("/admin/server");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "remove/{id}", method = {RequestMethod.GET})
	public void remove(@PathVariable long id, HttpServletResponse httpServletResponse)
	{
		serverService.delete(id);
		try
		{
			httpServletResponse.sendRedirect("/admin/server");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "edit/{id}", method = {RequestMethod.GET})
	public String edit(ModelMap model, @PathVariable long id)
	{
		Map<Long, String> loginServers = new TreeMap<>();
		for(Server server : serverService.findAll())
		{
			if(server.getType() == ServerType.LOGIN)
			{
				loginServers.put(server.getId(), server.getName());
			}
		}
		model.addAttribute("loginServers", loginServers);

		model.addAttribute("types", ServerType.getMap());
		model.addAttribute("server", serverService.findOne(id));
		model.addAttribute("servers", serverService.findAll());

		AdminController.addAttributes(model, "cp/admin/server/edit.vm");

		return "cp/index";
	}

	@RequestMapping(value = "edit", method = {RequestMethod.POST})
	public void edit(@ModelAttribute Server server, HttpServletResponse httpServletResponse)
	{
		Server temp = serverService.findOne(server.getId());
		temp.setType(server.getType());
		if(server.getType() == ServerType.LOGIN)
		{
			temp.setLoginId(0);
		}
		else
		{
			temp.setLoginId(server.getLoginId());
		}
		temp.setName(server.getName());
		temp.setIp(server.getIp());
		temp.setPort(server.getPort());
		temp.setXmlRpcL(server.getXmlRpcL());
		temp.setXmlRpcP(server.getXmlRpcP());
		temp.setEnable(server.isEnable());
		serverService.save(temp);
		try
		{
			httpServletResponse.sendRedirect("/admin/server");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}