package studio.lineage2.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.lineage2.cms.service.InfoService;
import studio.lineage2.cms.service.WheelService;

/**
 Eanseen
 02.06.2016
 */
@Controller @RequestMapping("/image") public class StaticController
{
	@Autowired private InfoService infoService;
	@Autowired private WheelService wheelService;

	@RequestMapping(value = "/info/{id}")
	@ResponseBody
	public byte[] info(@PathVariable long id)
	{
		return infoService.findOne(id).getImage();
	}

	@RequestMapping(value = "/wheel/{id}")
	@ResponseBody
	public byte[] wheel(@PathVariable long id)
	{
		return wheelService.findOne(id).getImage();
	}
}