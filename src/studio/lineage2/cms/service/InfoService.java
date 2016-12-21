package studio.lineage2.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.lineage2.cms.model.Info;
import studio.lineage2.cms.repository.InfoRepository;

import java.util.List;

/**
 Eanseen
 27.03.2016
 */
@Service public class InfoService
{
	@Autowired private InfoRepository infoRepository;

	public List<Info> findAll()
	{
		return infoRepository.findAll();
	}

	public Info findOne(long id)
	{
		return infoRepository.findOne(id);
	}

	public void save(Info info)
	{
		infoRepository.save(info);
	}

	public void delete(long infoId)
	{
		infoRepository.delete(infoId);
	}
}