package studio.lineage2.cms.controller.cp;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.lineage2.cms.model.GAccount;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.model.Server;
import studio.lineage2.cms.model.ServerType;
import studio.lineage2.cms.service.GAccountService;
import studio.lineage2.cms.service.ItemNameService;
import studio.lineage2.cms.service.ItemService;
import studio.lineage2.cms.service.ServerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 Eanseen
 08.06.2016
 */
@Controller @RequestMapping("/cp/item") public class CpItemController
{
	@Autowired private ItemService itemService;
	@Autowired private ItemNameService itemNameService;
	@Autowired private ServerService serverService;
	@Autowired private GAccountService gAccountService;

	@Autowired private Gson gson;

	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String index(ModelMap model)
	{
		List<Server> loginServers = new ArrayList<>();
		serverService.findAll().stream().filter(server->server.getType() == ServerType.LOGIN && server.isEnable()).forEach(loginServers::add);
		model.addAttribute("loginServers", loginServers);

		model.addAttribute("itemNames", itemNameService);

		MAccount mAccount = (MAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("items", itemService.findItemsByMAccountId(mAccount.getId()));

		model.addAttribute("page", "cp/user/item.vm");
		return "cp/index";
	}

	@RequestMapping(value = "/getGameServersByLoginServerId/{serverId}", method = {RequestMethod.GET})
	public
	@ResponseBody
	String getGameServersByLoginServerId(@PathVariable long serverId)
	{
		Map<Long, String> gameServers = new TreeMap<>();
		for(Server server : serverService.findAll())
		{
			if(server.getType() == ServerType.GAME && server.isEnable() && server.getLoginId() == serverId)
			{
				gameServers.put(server.getId(), server.getName());
			}
		}
		return gson.toJson(gameServers, Map.class);
	}

	@RequestMapping(value = "/getAccountsByLoginServerId/{serverId}", method = {RequestMethod.GET})
	public
	@ResponseBody
	String getAccountsByServerId(@PathVariable long serverId)
	{
		MAccount mAccount = (MAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<String> accounts = new ArrayList<>();
		for(GAccount account : gAccountService.findByMAccountIdAndServerId(mAccount.getId(), serverId))
		{
			accounts.add(account.getName());
		}

		return gson.toJson(accounts, List.class);
	}
}