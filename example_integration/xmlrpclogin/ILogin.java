package studio.lineage2.authserver.xmlrpclogin;

/**
 Eanseen
 12.06.2016
 */
public interface ILogin
{
	String reg(String login, String password);

	String change(String login, String password);
}