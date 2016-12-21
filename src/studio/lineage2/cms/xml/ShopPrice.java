package studio.lineage2.cms.xml;

import lombok.Data;

/**
 Eanseen
 29.06.2016
 */
public @Data class ShopPrice
{
	private int id;
	private int itemId;
	private long itemCount;
	private int priceItemId;
	private long priceItemCount;
}