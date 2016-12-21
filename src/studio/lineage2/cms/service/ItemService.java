package studio.lineage2.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.lineage2.cms.model.IMessage;
import studio.lineage2.cms.model.Item;
import studio.lineage2.cms.model.ItemLog;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.repository.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 Eanseen
 04.11.2015
 */
@Service public class ItemService
{
	@Autowired private ItemRepository itemRepository;
	@Autowired private ItemLogService itemLogService;
	@Autowired private MAccountService mAccountService;

	public void save(Item item)
	{
		itemRepository.save(item);
	}

	public void delete(Item Item)
	{
		itemRepository.delete(Item);
	}

	public List<Item> findItemsByMAccountId(long mAccountId)
	{
		return itemRepository.findAll().stream().filter(userItem->userItem.getMAccountId() == mAccountId && userItem.getItemId() != 100000 && userItem.getItemId() != 100001 && userItem.getItemId() != 100002).collect(Collectors.toList());
	}

	public Item findOne(long mAccountId, int itemId)
	{
		for(Item item : itemRepository.findAll())
		{
			if(item.getMAccountId() == mAccountId && item.getItemId() == itemId)
			{
				return item;
			}
		}
		return null;
	}

	public long getCount(long mAccountId, int itemId)
	{
		Item item = findOne(mAccountId, itemId);
		return item == null ? 0 : item.getItemCount();
	}

	public void addUserItem(long mAccountId, int itemId, long itemCount, String text)
	{
		if(itemCount < 1)
		{
			return;
		}

		MAccount mAccount = mAccountService.findOne(mAccountId);
		if(mAccount == null)
		{
			return;
		}

		mAccount.getItems().lock();

		Item item = findOne(mAccountId, itemId);
		if(item == null)
		{
			item = new Item();
			item.setMAccountId(mAccountId);
			item.setItemId(itemId);
		}
		item.incCount(itemCount);
		save(item);

		ItemLog itemLog = new ItemLog();
		itemLog.setMAccountId(item.getMAccountId());
		itemLog.setItemId(item.getItemId());
		itemLog.setItemCount(itemCount);
		itemLog.setText(text);
		itemLogService.save(itemLog);

		mAccount.getItems().unlock();
	}

	public boolean removeUserItem(long mAccountId, int itemId, long itemCount, String text)
	{
		if(itemCount < 1)
		{
			return false;
		}

		MAccount mAccount = mAccountService.findOne(mAccountId);
		if(mAccount == null)
		{
			return false;
		}

		mAccount.getItems().lock();

		Item item = findOne(mAccountId, itemId);
		if(item == null || item.getItemCount() < itemCount)
		{
			return false;
		}
		item.decCount(itemCount);

		if(item.getItemCount() > 0)
		{
			save(item);
		}
		else
		{
			delete(item);
		}

		ItemLog itemLog = new ItemLog();
		itemLog.setMAccountId(item.getMAccountId());
		itemLog.setItemId(item.getItemId());
		itemLog.setItemCount(-itemCount);
		itemLog.setText(text);
		itemLogService.save(itemLog);

		mAccount.getItems().unlock();

		return true;
	}

	public IMessage addUserItem(String mAccountId, String itemId, String itemCount, String text)
	{
		try
		{
			addUserItem(Long.parseLong(mAccountId), Integer.parseInt(itemId), Long.parseLong(itemCount), text);
		}
		catch(Exception e)
		{
			return new IMessage(IMessage.Type.FAIL, "Что-то пошло не так");
		}
		return new IMessage(IMessage.Type.SUCCESS, "Успешно начислено");
	}
}