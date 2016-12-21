package studio.lineage2.cms;

import studio.lineage2.cms.model.IMessage;

/**
 Eanseen
 12.06.2016
 */
public interface ILogin
{
	IMessage reg(long serverId, String login, String password);

	IMessage change(long serverId, String login, String password);
}