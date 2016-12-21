package studio.lineage2.gameserver.xmlrpcgame;

import org.apache.xmlrpc.common.XmlRpcHttpRequestConfig;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;
import studio.lineage2.gameserver.options.Options;

import java.net.InetAddress;

/**
 Eanseen
 12.06.2016
 */
public class XmlRpcGameServer
{
	private static XmlRpcGameServer ourInstance = new XmlRpcGameServer();

	private XmlRpcGameServer()
	{
		try
		{
			PropertyHandlerMapping phm = new PropertyHandlerMapping();

			phm.addHandler("XmlRpcGame", XmlRpcGameService.class);

			phm.setAuthenticationHandler(xmlRpcRequest->
			{
				XmlRpcHttpRequestConfig config = (XmlRpcHttpRequestConfig) xmlRpcRequest.getConfig();
				return Options.optionGameServer.xmlrpcLogin().equals(config.getBasicUserName()) && Options.optionGameServer.xmlrpcPassword().equals(config.getBasicPassword());
			});

			WebServer webServer = new WebServer(Options.optionGameServer.xmlrpcPort(), InetAddress.getByName(Options.optionGameServer.xmlrpcIp()));

			XmlRpcServer xmlServer = webServer.getXmlRpcServer();
			xmlServer.setHandlerMapping(phm);

			XmlRpcServerConfigImpl xmlRpcServerConfig = (XmlRpcServerConfigImpl) xmlServer.getConfig();
			xmlRpcServerConfig.setEnabledForExtensions(true);

			webServer.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static XmlRpcGameServer getInstance()
	{
		return ourInstance;
	}
}