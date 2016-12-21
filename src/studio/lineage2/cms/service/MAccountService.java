package studio.lineage2.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.repository.MAccountRepository;

import java.util.List;

@Service public class MAccountService
{
	@Autowired private MAccountRepository mAccountRepository;

	public void save(MAccount user)
	{
		mAccountRepository.save(user);
	}

	public List<MAccount> findAll()
	{
		return mAccountRepository.findAll();
	}

	public MAccount findOne(long mAccountId)
	{
		return mAccountRepository.findOne(mAccountId);
	}

	public MAccount findByUsername(String username) throws UsernameNotFoundException
	{
		for(MAccount mAccount : mAccountRepository.findAll())
		{
			if(mAccount.getUsername().toLowerCase().equals(username.toLowerCase()))
			{
				return mAccount;
			}
		}
		throw new UsernameNotFoundException(username);
	}

	public boolean containsByUsername(String username)
	{
		for(MAccount mAccount : mAccountRepository.findAll())
		{
			if(mAccount.getUsername().toLowerCase().equals(username.toLowerCase()))
			{
				return true;
			}
		}
		return false;
	}

	public MAccount findByVoteName(String voteName)
	{
		for(MAccount mAccount : mAccountRepository.findAll())
		{
			if(mAccount.getVoteName() != null && mAccount.getVoteName().equals(voteName))
			{
				return mAccount;
			}
		}
		return null;
	}

	public boolean containsByVoteName(String voteName)
	{
		for(MAccount user : mAccountRepository.findAll())
		{
			if(user.getVoteName() != null && user.getVoteName().equals(voteName))
			{
				return true;
			}
		}
		return false;
	}
}