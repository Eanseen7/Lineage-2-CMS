package studio.lineage2.cms.model;

import com.google.gson.Gson;
import lombok.Getter;

/**
 Eanseen
 26.10.2015
 */
public class IMessage
{
	private @Getter Type type;
	private @Getter String message;

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
		Gson gson = new Gson();
		return gson.fromJson(message, type);
	}

	public enum Type
	{
		SUCCESS,
		FAIL
	}
}