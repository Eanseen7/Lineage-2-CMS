package studio.lineage2.cms.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 Eanseen
 08.06.2016
 */
@Service public class ItemNameService
{
	private Map<Integer, String> itemNames = new HashMap<>();

	public ItemNameService()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("./public/strings/items.txt"));
			String line;
			while((line = reader.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line, "\t");
				int itemId = Integer.parseInt(st.nextToken());
				String itemName = st.nextToken();
				itemNames.put(itemId, itemName);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getItemName(int itemId)
	{
		return itemNames.get(itemId);
	}
}