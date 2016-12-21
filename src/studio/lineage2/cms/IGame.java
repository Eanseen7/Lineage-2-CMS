package studio.lineage2.cms;

import studio.lineage2.cms.model.IMessage;

/**
 Eanseen
 12.06.2016
 */
public interface IGame
{
	String getPlayersNameByAccount(long loginServerId, long gameServerId, String account);

	IMessage addItemByPlayerName(long loginServerId, long gameServerId, String account, String playerName, int itemId, long itemCount);

	String getTopPvP(long loginServerId, long gameServerId, int count);

	String getTopPK(long loginServerId, long gameServerId, int count);
}