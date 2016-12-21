package studio.lineage2.cms.controller.cp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.service.ItemLogService;
import studio.lineage2.cms.service.ItemNameService;

/**
 Eanseen
 08.06.2016
 */
@Controller @RequestMapping("/cp/itemlog") public class CpItemLogController
{
	@Autowired private ItemLogService itemLogService;
	@Autowired private ItemNameService itemNameService;

	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String index(ModelMap model)
	{
		MAccount mAccount = (MAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("itemlogs", itemLogService.findAll(mAccount.getId()));

		model.addAttribute("itemNames", itemNameService);

		model.addAttribute("page", "cp/user/itemlog.vm");
		return "cp/index";
	}
}