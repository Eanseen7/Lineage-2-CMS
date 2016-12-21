package studio.lineage2.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.model.VoteMMOTOP;
import studio.lineage2.cms.repository.VoteMMOTOPRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

/**
 Eanseen
 04.11.2015
 */
@Service public class VoteMMOTOPService
{
	@Value("${vote.mmotop.enable}") private boolean mmotopEnable;
	@Value("${vote.mmotop.id}") private int mmotopId;
	@Value("${vote.mmotop.link}") private String mmotopLink;
	@Value("${vote.mmotop.itemId}") private int mmotopItemId;
	@Value("${vote.mmotop.itemCount}") private long mmotopItemCount;

	@Autowired private VoteMMOTOPRepository voteMMOTOPRepository;
	@Autowired private MAccountService mAccountService;
	@Autowired private ItemService itemService;

	public void save(VoteMMOTOP voteMMOTOP)
	{
		voteMMOTOPRepository.save(voteMMOTOP);
	}

	public boolean contains(long voteId)
	{
		for(VoteMMOTOP voteMMOTOP : voteMMOTOPRepository.findAll())
		{
			if(voteMMOTOP.getVoteId() == voteId)
			{
				return true;
			}
		}
		return false;
	}

	@Scheduled(initialDelay = 1000, fixedRate = 60000)
	public void load()
	{
		if(!mmotopEnable)
		{
			return;
		}
		try
		{
			URL url = new URL(mmotopLink);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			String line;
			while((line = reader.readLine()) != null)
			{
				if(line.startsWith("Нет данных") || line.startsWith("QRATOR HTTP") || line.contains("Попробуйте обновить страницу"))
				{
					break;
				}

				StringTokenizer st = new StringTokenizer(line, "\t");
				long voteId = Long.parseLong(st.nextToken());
				st.nextToken();
				st.nextToken();
				String name = st.nextToken();

				if(contains(voteId))
				{
					continue;
				}

				VoteMMOTOP voteMMOTOP = new VoteMMOTOP();
				voteMMOTOP.setVoteId(voteId);
				voteMMOTOP.setName(name);
				voteMMOTOP.setTake(false);

				save(voteMMOTOP);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		for(VoteMMOTOP voteMMOTOP : voteMMOTOPRepository.findAll())
		{
			if(voteMMOTOP.isTake())
			{
				continue;
			}
			MAccount mAccount = mAccountService.findByVoteName(voteMMOTOP.getName());
			if(mAccount == null)
			{
				continue;
			}
			voteMMOTOP.setTake(true);
			save(voteMMOTOP);
			itemService.addUserItem(mAccount.getId(), mmotopItemId, mmotopItemCount, "Голосование в MMOTOP");
		}
	}
}