package studio.lineage2.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import studio.lineage2.cms.xml.ShopPrice;
import studio.lineage2.cms.xml.WheelTicketPrice;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;

@SpringBootApplication @EnableAutoConfiguration @ComponentScan @EnableScheduling @EnableWebMvc public class CmsApplication
{
	private static Map<Integer, WheelTicketPrice> wheelTicketPrices = new TreeMap<>();
	private static Map<Integer, ShopPrice> shopPrices = new TreeMap<>();

	public static void main(String[] args)
	{
		loadWheel();
		loadShop();
		SpringApplication.run(CmsApplication.class, args);
	}

	public static Map<Integer, WheelTicketPrice> getWheelTicketPrices()
	{
		return wheelTicketPrices;
	}

	public static Map<Integer, ShopPrice> getShopPrices()
	{
		return shopPrices;
	}

	private static void loadWheel()
	{
		try
		{
			File file = new File("./public/data/wheel_ticket_price.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			doc.getDocumentElement().normalize();

			NodeList root = doc.getElementsByTagName("item");

			for(int i = 0; i < root.getLength(); i++)
			{
				Node node = root.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element) node;
					WheelTicketPrice price = new WheelTicketPrice();
					price.setItemId(Integer.parseInt(element.getAttribute("id")));
					price.setItemCount(Integer.parseInt(element.getAttribute("count")));
					wheelTicketPrices.put(price.getItemId(), price);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void loadShop()
	{
		try
		{
			File file = new File("./public/data/shop_price.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			doc.getDocumentElement().normalize();

			NodeList root = doc.getElementsByTagName("item");

			for(int i = 0; i < root.getLength(); i++)
			{
				Node node = root.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element) node;
					ShopPrice price = new ShopPrice();
					price.setId(i + 1);
					price.setItemId(Integer.parseInt(element.getAttribute("itemId")));
					price.setItemCount(Integer.parseInt(element.getAttribute("itemCount")));
					price.setPriceItemId(Integer.parseInt(element.getAttribute("priceItemId")));
					price.setPriceItemCount(Integer.parseInt(element.getAttribute("priceItemCount")));
					shopPrices.put(price.getId(), price);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}