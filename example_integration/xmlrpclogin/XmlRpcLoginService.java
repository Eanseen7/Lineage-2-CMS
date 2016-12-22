package studio.lineage2.authserver.xmlrpclogin;

import com.google.gson.Gson;
import studio.lineage2.authserver.Config;
import studio.lineage2.authserver.accounts.Account;

/**
 Eanseen
 12.06.2016
 */
public class XmlRpcLoginService implements ILogin
{
	private Gson gson = new Gson();

	protected <T> String json(T data)
	{
		return gson.toJson(data);
	}

	@Override
	public String reg(String login, String password)
	{
		Account account = new Account(login);
		account.restore();

		if(account.getPasswordHash() != null)
		{
			return json(new IMessage(IMessage.Type.FAIL, "Такой аккаунт уже существует"));
		}

		try
		{
			String passwordHash = Config.DEFAULT_CRYPT.encrypt(password);
			account.setAllowedIP("");
			account.setAllowedHwid("");
			account.setPasswordHash(passwordHash);
			account.save();
			return json(new IMessage(IMessage.Type.SUCCESS));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return json(new IMessage(IMessage.Type.FAIL, "Что-то пошло не так"));
	}

	@Override
	public String change(String login, String password)
	{
		Account account = new Account(login);
		account.restore();

		if(account.getPasswordHash() == null)
		{
			return json(new IMessage(IMessage.Type.FAIL, "Такой аккаунт не существует"));
		}

		try
		{
			String passwordHash = Config.DEFAULT_CRYPT.encrypt(password);
			account.setAllowedIP("");
			account.setAllowedHwid("");
			account.setPasswordHash(passwordHash);
			account.update();
			return json(new IMessage(IMessage.Type.SUCCESS, "Смена пароля прошла успешно"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return json(new IMessage(IMessage.Type.FAIL, "Что-то пошло не так"));
	}
}