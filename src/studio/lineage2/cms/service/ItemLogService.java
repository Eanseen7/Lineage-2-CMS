package studio.lineage2.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.lineage2.cms.model.ItemLog;
import studio.lineage2.cms.repository.ItemLogRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 Eanseen
 04.11.2015
 */
@Service public class ItemLogService
{
	@Autowired private ItemLogRepository itemLogRepository;

	public void save(ItemLog userItemLog)
	{
		itemLogRepository.save(userItemLog);
	}

	public List<ItemLog> findAll(long mAccountId)
	{
		List<ItemLog> itemLogs = itemLogRepository.findAll().stream().filter(itemLog->itemLog.getMAccountId() == mAccountId).collect(Collectors.toList());
		Collections.reverse(itemLogs);
		return itemLogs;
	}
}