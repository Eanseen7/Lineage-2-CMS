package studio.lineage2.gameserver.xmlrpcgame;

import com.google.gson.Gson;

/**
 Eanseen
 26.10.2015
 */
public class IMessage
{
	private static Gson gson = new Gson();
	private Type type;
	private String message;

	public IMessage(Type type)
	{
		this.type = type;
		this.message = "";
	}

	public IMessage(Type type, String message)
	{
		this.type = type;
		this.message = message;
	}

	public <T> T getData(Class<T> type)
	{
		return gson.fromJson(message, type);
	}

	public enum Type
	{
		SUCCESS,
		FAIL
	}
}