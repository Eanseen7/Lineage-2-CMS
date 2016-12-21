package studio.lineage2.cms.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import studio.lineage2.cms.model.Info;
import studio.lineage2.cms.service.InfoService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 Eanseen
 02.06.2016
 */
@Controller @RequestMapping("/admin/info") public class AdminInfoController
{
	@Autowired private InfoService infoService;

	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String index(ModelMap model)
	{
		model.addAttribute("info", new Info());

		List<Info> infos = infoService.findAll();
		Collections.reverse(infos);
		model.addAttribute("infos", infos);

		AdminController.addAttributes(model, "cp/admin/info/index.vm");
		return "cp/index";
	}

	@RequestMapping(value = "add", method = {RequestMethod.POST})
	public String add(ModelMap model, @ModelAttribute Info info, @RequestParam("file") MultipartFile file)
	{
		info.setDate(System.currentTimeMillis());
		try
		{
			info.setImage(file.getBytes());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		infoService.save(info);
		return index(model);
	}

	@RequestMapping(value = "remove/{id}", method = {RequestMethod.GET})
	public String remove(ModelMap model, @PathVariable long id)
	{
		infoService.delete(id);
		return index(model);
	}

	@RequestMapping(value = "edit/{id}", method = {RequestMethod.GET})
	public String edit(ModelMap model, @PathVariable long id)
	{
		model.addAttribute("info", infoService.findOne(id));

		AdminController.addAttributes(model, "cp/admin/info/edit.vm");

		return "cp/index";
	}

	@RequestMapping(value = "edit", method = {RequestMethod.POST})
	public String edit(ModelMap model, long id, @ModelAttribute Info temp, @RequestParam("file") MultipartFile file)
	{
		Info info = infoService.findOne(id);
		info.setTitle(temp.getTitle());
		info.setContent(temp.getContent());
		info.setLink(temp.getLink());
		try
		{
			if(file.getSize() > 0)
			{
				info.setImage(file.getBytes());
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		infoService.save(info);
		return index(model);
	}
}