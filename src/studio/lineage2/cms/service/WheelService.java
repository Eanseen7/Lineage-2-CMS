package studio.lineage2.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.lineage2.cms.model.Wheel;
import studio.lineage2.cms.repository.WheelRepository;
import studio.lineage2.cms.utils.Rnd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 Eanseen
 05.11.2015
 */
@Service public class WheelService
{
	@Autowired private WheelRepository wheelRepository;

	public void save(Wheel wheel)
	{
		wheelRepository.save(wheel);
	}

	public Wheel findOne(long id)
	{
		return wheelRepository.findOne(id);
	}

	public List<Wheel> findAll()
	{
		Comparator<Wheel> wheelComparator = (left, right)->
		{
			if(left.getChance() < right.getChance())
			{
				return -1;
			}
			else
			{
				return 1;
			}
		};
		List<Wheel> wheels = wheelRepository.findAll();
		wheels.sort(wheelComparator);
		return wheels;
	}

	public Wheel findRandom()
	{
		List<Wheel> wheels = new ArrayList<>();
		for(Wheel wheel : findAll())
		{
			if(Rnd.chance(wheel.getChance()))
			{
				wheels.add(wheel);
			}
		}
		Collections.shuffle(wheels);
		return wheels.isEmpty() ? null : wheels.get(0);
	}

	public void delete(long id)
	{
		wheelRepository.delete(id);
	}
}