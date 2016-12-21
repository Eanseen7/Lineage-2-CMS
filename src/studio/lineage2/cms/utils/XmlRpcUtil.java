package studio.lineage2.cms.utils;

import com.google.gson.Gson;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import studio.lineage2.cms.model.IMessage;
import studio.lineage2.cms.model.Server;

import java.net.URL;

/**
 Eanseen
 07.06.2016
 */
public class XmlRpcUtil
{
	public static IMessage getMessage(Server server, String method, Object... objects)
	{
		try
		{
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL("http://" + server.getIp() + ":" + server.getPort()));
			config.setBasicUserName(server.getXmlRpcL());
			config.setBasicPassword(server.getXmlRpcP());
			config.setConnectionTimeout(30000);
			config.setEnabledForExtensions(true);
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			String result = (String) client.execute(method, objects);
			Gson gson = new Gson();
			return gson.fromJson(result, IMessage.class);
		}
		catch(Exception e)
		{
			return new IMessage(IMessage.Type.FAIL, "Нет связи с сервером, попробуйте позже");
		}
	}
}