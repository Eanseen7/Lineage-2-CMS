package studio.lineage2.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.lineage2.cms.model.Server;
import studio.lineage2.cms.repository.ServerRepository;

import java.util.List;

/**
 Eanseen
 07.06.2016
 */
@Service public class ServerService
{
	@Autowired private ServerRepository serverRepository;

	public void save(Server server)
	{
		serverRepository.save(server);
	}

	public Server findOne(long id)
	{
		return serverRepository.findOne(id);
	}

	public List<Server> findAll()
	{
		return serverRepository.findAll();
	}

	public void delete(long id)
	{
		serverRepository.delete(id);
	}
}