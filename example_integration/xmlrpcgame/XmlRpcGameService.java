package studio.lineage2.gameserver.xmlrpcgame;

import com.google.gson.Gson;
import studio.lineage2.commons.dbutils.DbUtils;
import studio.lineage2.gameserver.dao.CharacterDAO;
import studio.lineage2.gameserver.database.DatabaseFactory;
import studio.lineage2.gameserver.model.GameObjectsStorage;
import studio.lineage2.gameserver.model.Player;
import studio.lineage2.gameserver.taskmanager.DelayedItemsManager;
import studio.lineage2.gameserver.utils.ItemFunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 Eanseen
 12.06.2016
 */
public class XmlRpcGameService implements IGame
{
	private Gson gson = new Gson();

	protected <T> String json(T data)
	{
		return gson.toJson(data);
	}

	@Override
	public String getPlayersNameByAccount(String account)
	{
		return json(new IMessage(IMessage.Type.SUCCESS, json(getPlayersNameByAccountData(account))));
	}

	@Override
	public String addItemByPlayerName(String playerName, int itemId, long itemCount)
	{
		Player player = GameObjectsStorage.getPlayer(playerName);
		if(player != null && player.isOnline())
		{
			ItemFunctions.addItem(player, itemId, itemCount, true);
		}
		else
		{
			int playerId = CharacterDAO.getInstance().getObjectIdByName(playerName);
			DelayedItemsManager.addDelayed(playerId, itemId, itemCount, 0, "");
		}
		return json(new IMessage(IMessage.Type.SUCCESS));
	}

	@Override
	public String getTopPvP(int count)
	{
		return json(new IMessage(IMessage.Type.SUCCESS, json(getTopPvPData(count))));
	}

	@Override
	public String getTopPK(int count)
	{
		return json(new IMessage(IMessage.Type.SUCCESS, json(getTopPKData(count))));
	}

	public List<String> getPlayersNameByAccountData(String account)
	{
		List<String> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rset = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			statement = con.prepareStatement("SELECT char_name FROM characters WHERE account_name=?");
			statement.setString(1, account);
			rset = statement.executeQuery();
			while(rset.next())
			{
				list.add(rset.getString("char_name"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(con, statement, rset);
		}

		return list;
	}

	public Map<String, Integer> getTopPvPData(int count)
	{
		Map<String, Integer> map = new LinkedHashMap<>();

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rset = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			statement = con.prepareStatement("SELECT char_name, pvpkills FROM characters ORDER BY pvpkills DESC LIMIT ?");
			statement.setInt(1, count);
			rset = statement.executeQuery();
			while(rset.next())
			{
				map.put(rset.getString("char_name"), rset.getInt("pvpkills"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(con, statement, rset);
		}

		return map;
	}

	public Map<String, Integer> getTopPKData(int count)
	{
		Map<String, Integer> map = new LinkedHashMap<>();

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rset = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			statement = con.prepareStatement("SELECT char_name, pkkills FROM characters ORDER BY pkkills DESC LIMIT ?");
			statement.setInt(1, count);
			rset = statement.executeQuery();
			while(rset.next())
			{
				map.put(rset.getString("char_name"), rset.getInt("pkkills"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(con, statement, rset);
		}

		return map;
	}
}