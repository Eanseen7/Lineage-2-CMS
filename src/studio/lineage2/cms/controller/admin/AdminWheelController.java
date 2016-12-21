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
import studio.lineage2.cms.model.Wheel;
import studio.lineage2.cms.service.ItemNameService;
import studio.lineage2.cms.service.WheelService;

import java.io.IOException;

/**
 Eanseen
 05.11.2015
 */
@Controller @RequestMapping("/admin/wheel") public class AdminWheelController
{
	@Autowired private WheelService wheelService;
	@Autowired private ItemNameService itemNameService;

	@RequestMapping(method = {RequestMethod.GET})
	public String show(ModelMap model)
	{
		model.addAttribute("wheel", new Wheel());
		model.addAttribute("wheelList", wheelService.findAll());

		model.addAttribute("itemNames", itemNameService);

		AdminController.addAttributes(model, "cp/admin/wheel/index.vm");
		return "cp/index";
	}

	@RequestMapping(value = "add", method = {RequestMethod.POST})
	public String add(ModelMap model, @ModelAttribute Wheel wheel, @RequestParam("file") MultipartFile file)
	{
		try
		{
			wheel.setImage(file.getBytes());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		wheelService.save(wheel);
		return show(model);
	}

	@RequestMapping(value = "remove/{id}", method = {RequestMethod.GET})
	public String remove(ModelMap model, @PathVariable long id)
	{
		wheelService.delete(id);
		return show(model);
	}

	@RequestMapping(value = "edit/{id}", method = {RequestMethod.GET})
	public String edit(ModelMap model, @PathVariable long id)
	{
		model.addAttribute("wheel", wheelService.findOne(id));
		model.addAttribute("wheelList", wheelService.findAll());

		model.addAttribute("itemNames", itemNameService);

		AdminController.addAttributes(model, "cp/admin/wheel/edit.vm");
		return "cp/index";
	}

	@RequestMapping(value = "edit", method = {RequestMethod.POST})
	public String edit(ModelMap model, long id, @ModelAttribute Wheel wheel, @RequestParam("file") MultipartFile file)
	{
		Wheel wheel1 = wheelService.findOne(id);
		wheel1.setItemId(wheel.getItemId());
		wheel1.setItemCount(wheel.getItemCount());
		wheel1.setChance(wheel.getChance());
		try
		{
			if(file.getSize() > 0)
			{
				wheel1.setImage(file.getBytes());
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		wheelService.save(wheel1);
		return show(model);
	}
}