package studio.lineage2.cms.controller.enter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 Eanseen
 04.12.2016
 */
@Controller @RequestMapping("/enter") public class EnterController
{
	@RequestMapping(method = {RequestMethod.GET})
	public String index(ModelMap model)
	{
		model.addAttribute("page", "cp/enter/index.vm");
		return "cp/index";
	}
}