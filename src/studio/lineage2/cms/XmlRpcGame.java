package studio.lineage2.cms;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.lineage2.cms.model.GAccount;
import studio.lineage2.cms.model.IMessage;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.model.Server;
import studio.lineage2.cms.model.ServerType;
import studio.lineage2.cms.service.GAccountService;
import studio.lineage2.cms.service.ItemService;
import studio.lineage2.cms.service.ServerService;
import studio.lineage2.cms.utils.XmlRpcUtil;

import java.util.Collections;
import java.util.List;

/**
 Eanseen
 12.06.2016
 */
@Controller @RequestMapping("/XmlRpcGame") public class XmlRpcGame implements IGame
{
	@Autowired private Gson gson;
	@Autowired private ServerService serverService;
	@Autowired private GAccountService gAccountService;
	@Autowired private ItemService itemService;

	@Override
	@RequestMapping(value = "/getPlayersNameByAccount", method = {RequestMethod.GET}, produces = {"application/plane; charset=UTF-8"})
	public
	@ResponseBody
	String getPlayersNameByAccount(long loginServerId, long gameServerId, String account)
	{
		Server loginServer = serverService.findOne(loginServerId);
		Server gameServer = serverService.findOne(gameServerId);

		if(loginServer == null || loginServer.getType() != ServerType.LOGIN || !loginServer.isEnable())
		{
			return gson.toJson(Collections.emptyList(), List.class);
		}

		if(gameServer == null || gameServer.getType() != ServerType.GAME || !gameServer.isEnable() || gameServer.getLoginId() != loginServer.getId())
		{
			return gson.toJson(Collections.emptyList(), List.class);
		}

		MAccount user = (MAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<GAccount> gAccounts = gAccountService.findByMAccountIdAndServerId(user.getId(), loginServerId);
		if(gAccounts.isEmpty())
		{
			return gson.toJson(Collections.emptyList(), List.class);
		}

		return XmlRpcUtil.getMessage(gameServer, "XmlRpcGame.getPlayersNameByAccount", account.toLowerCase()).getMessage();
	}

	@Override
	@RequestMapping(value = "/addItemByPlayerName", method = {RequestMethod.POST})
	public
	@ResponseBody
	IMessage addItemByPlayerName(long loginServerId, long gameServerId, String account, String playerName, int itemId, long itemCount)
	{
		Server loginServer = serverService.findOne(loginServerId);
		Server gameServer = serverService.findOne(gameServerId);

		if(loginServer == null || loginServer.getType() != ServerType.LOGIN || !loginServer.isEnable())
		{
			return new IMessage(IMessage.Type.FAIL, "Что-то пошло не так");
		}

		if(gameServer == null || gameServer.getType() != ServerType.GAME || !gameServer.isEnable() || gameServer.getLoginId() != loginServer.getId())
		{
			return new IMessage(IMessage.Type.FAIL, "Что-то пошло не так");
		}

		MAccount mAccount = (MAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<GAccount> gAccounts = gAccountService.findByMAccountIdAndServerId(mAccount.getId(), loginServerId);
		if(gAccounts.isEmpty())
		{
			return new IMessage(IMessage.Type.FAIL, "Что-то пошло не так");
		}

		List<String> playersName = XmlRpcUtil.getMessage(gameServer, "XmlRpcGame.getPlayersNameByAccount", account.toLowerCase()).getData(List.class);
		if(playersName.isEmpty())
		{
			return new IMessage(IMessage.Type.FAIL, "Что-то пошло не так");
		}

		for(String p : playersName)
		{
			if(p.equals(playerName))
			{
				if(itemService.removeUserItem(mAccount.getId(), itemId, itemCount, "Перевод в игру"))
				{
					return XmlRpcUtil.getMessage(gameServer, "XmlRpcGame.addItemByPlayerName", playerName, itemId, itemCount);
				}
				else
				{
					return new IMessage(IMessage.Type.FAIL, "Что-то пошло не так");
				}
			}
		}

		return new IMessage(IMessage.Type.FAIL, "Что-то пошло не так");
	}

	@Override
	@RequestMapping(value = "/getTopPvP", method = {RequestMethod.GET}, produces = {"application/plane; charset=UTF-8"})
	public
	@ResponseBody
	String getTopPvP(long loginServerId, long gameServerId, int count)
	{
		Server loginServer = serverService.findOne(loginServerId);
		Server gameServer = serverService.findOne(gameServerId);

		if(loginServer == null || loginServer.getType() != ServerType.LOGIN || !loginServer.isEnable())
		{
			return gson.toJson(Collections.emptyList(), List.class);
		}

		if(gameServer == null || gameServer.getType() != ServerType.GAME || !gameServer.isEnable() || gameServer.getLoginId() != loginServer.getId())
		{
			return gson.toJson(Collections.emptyList(), List.class);
		}

		return XmlRpcUtil.getMessage(gameServer, "XmlRpcGame.getTopPvP", count).getMessage();
	}

	@Override
	@RequestMapping(value = "/getTopPK", method = {RequestMethod.GET}, produces = {"application/plane; charset=UTF-8"})
	public
	@ResponseBody
	String getTopPK(long loginServerId, long gameServerId, int count)
	{
		Server loginServer = serverService.findOne(loginServerId);
		Server gameServer = serverService.findOne(gameServerId);

		if(loginServer == null || loginServer.getType() != ServerType.LOGIN || !loginServer.isEnable())
		{
			return gson.toJson(Collections.emptyList(), List.class);
		}

		if(gameServer == null || gameServer.getType() != ServerType.GAME || !gameServer.isEnable() || gameServer.getLoginId() != loginServer.getId())
		{
			return gson.toJson(Collections.emptyList(), List.class);
		}

		return XmlRpcUtil.getMessage(gameServer, "XmlRpcGame.getTopPK", count).getMessage();
	}
}