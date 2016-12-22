package studio.lineage2.authserver.xmlrpclogin;

import org.apache.xmlrpc.common.XmlRpcHttpRequestConfig;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;
import studio.lineage2.authserver.options.Options;

import java.net.InetAddress;

/**
 Eanseen
 12.06.2016
 */
public class XmlRpcLoginServer
{
	private static XmlRpcLoginServer ourInstance = new XmlRpcLoginServer();

	private XmlRpcLoginServer()
	{
		try
		{
			PropertyHandlerMapping phm = new PropertyHandlerMapping();

			phm.addHandler("XmlRpcLogin", XmlRpcLoginService.class);

			phm.setAuthenticationHandler(xmlRpcRequest->
			{
				XmlRpcHttpRequestConfig config = (XmlRpcHttpRequestConfig) xmlRpcRequest.getConfig();
				return Options.optionLoginServer.xmlrpcLogin().equals(config.getBasicUserName()) && Options.optionLoginServer.xmlrpcPassword().equals(config.getBasicPassword());
			});

			WebServer webServer = new WebServer(Options.optionLoginServer.xmlrpcPort(), InetAddress.getByName(Options.optionLoginServer.xmlrpcIp()));

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

	public static XmlRpcLoginServer getInstance()
	{
		return ourInstance;
	}
}