package studio.lineage2.gameserver.xmlrpcgame;

/**
 Eanseen
 12.06.2016
 */
public interface IGame
{
	String getPlayersNameByAccount(String account);

	String addItemByPlayerName(String playerName, int itemId, long itemCount);

	String getTopPvP(int count);

	String getTopPK(int count);
}