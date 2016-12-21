package studio.lineage2.cms.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 Eanseen
 28.10.2015
 */
@Controller @RequestMapping("/admin") public class AdminController
{
	public static void addAttributes(ModelMap model, String page)
	{
		addAttributes(model, page, "");
	}

	public static void addAttributes(ModelMap model, String page, String menu)
	{
		model.addAttribute("header", "/cp/header.vm");
		model.addAttribute("body", "/cp/body.vm");
		model.addAttribute("footer", "/cp/footer.vm");

		model.addAttribute("menu2", "cp/admin/menu.vm");
		if(!menu.isEmpty())
		{
			model.addAttribute("menu3", menu);
		}

		model.addAttribute("page", page);
	}

	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String show(ModelMap model)
	{
		addAttributes(model, "cp/admin/index.vm");
		return "cp/index";
	}

	@RequestMapping(value = "/restart", method = {RequestMethod.GET})
	public void restart()
	{
		System.exit(2);
	}
}