package studio.lineage2.cms.utils;

import java.util.Random;

public class Rnd
{
	private static final String password = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String prefix = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random rnd = new Random();

	public static String getPassword()
	{
		StringBuilder sb = new StringBuilder(32);
		for(int i = 0; i < 32; i++)
		{
			sb.append(password.charAt(rnd.nextInt(password.length())));
		}
		return sb.toString();
	}

	public static String getPrefix()
	{
		StringBuilder sb = new StringBuilder(3);
		for(int i = 0; i < 3; i++)
		{
			sb.append(prefix.charAt(rnd.nextInt(prefix.length())));
		}
		return sb.toString();
	}

	public static int get(int max)
	{
		return get(0, max);
	}

	public static int get(int min, int max)
	{
		return rnd.nextInt(max - min) + min;
	}

	public static boolean chance(double chance)
	{
		return rnd.nextDouble() <= chance / 100.;
	}
}