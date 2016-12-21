package studio.lineage2.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.lineage2.cms.model.GAccount;
import studio.lineage2.cms.repository.GAccountRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 Eanseen
 07.06.2016
 */
@Service public class GAccountService
{
	@Autowired private GAccountRepository gAccountRepository;

	public void save(GAccount user)
	{
		gAccountRepository.save(user);
	}

	public List<GAccount> findByMAccountIdAndServerId(long mAccoundId, long serverId)
	{
		return gAccountRepository.findAll().stream().filter(gAccount->gAccount.getMAccountId() == mAccoundId && gAccount.getServerId() == serverId).collect(Collectors.toList());
	}
}