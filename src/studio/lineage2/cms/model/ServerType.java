package studio.lineage2.cms.model;

import java.util.Map;
import java.util.TreeMap;

/**
 Eanseen
 07.06.2016
 */
public enum ServerType
{
	LOGIN,
	GAME;

	public static Map<ServerType, ServerType> getMap()
	{
		Map<ServerType, ServerType> types = new TreeMap<>();
		for(ServerType serverType : values())
		{
			types.put(serverType, serverType);
		}
		return types;
	}
}