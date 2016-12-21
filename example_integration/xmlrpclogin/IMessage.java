package studio.lineage2.authserver.xmlrpclogin;

import com.google.gson.Gson;

/**
 Eanseen
 26.10.2015
 */
public class IMessage
{
	private Type type;
	private String message;

	private static Gson gson = new Gson();

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

	public enum Type
	{
		SUCCESS,
		FAIL
	}

	public <T> T getData(Class<T> type)
	{
		return gson.fromJson(message, type);
	}
}