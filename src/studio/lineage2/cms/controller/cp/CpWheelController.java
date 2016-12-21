package studio.lineage2.cms.controller.cp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.lineage2.cms.CmsApplication;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.model.Wheel;
import studio.lineage2.cms.service.ForumService;
import studio.lineage2.cms.service.ItemNameService;
import studio.lineage2.cms.service.ItemService;
import studio.lineage2.cms.service.MAccountService;
import studio.lineage2.cms.service.WheelService;
import studio.lineage2.cms.xml.WheelTicketPrice;

/**
 Eanseen
 28.06.2016
 */
@Controller @RequestMapping("/wheel") public class CpWheelController
{
	@Autowired private ItemService itemService;
	@Autowired private WheelService wheelService;
	@Autowired private ItemNameService itemNameService;
	@Autowired private ForumService forumService;
	@Autowired private MAccountService mAccountService;

	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String show(ModelMap model)
	{
		model.addAttribute("wheelList", wheelService.findAll());
		model.addAttribute("forumService", forumService);
		model.addAttribute("itemService", itemService);
		model.addAttribute("itemNames", itemNameService);
		model.addAttribute("prices", CmsApplication.getWheelTicketPrices().values());

		model.addAttribute("page", "cp/user/wheel.vm");
		return "cp/index";
	}

	@RequestMapping(value = "/getTicket/{itemId}", method = {RequestMethod.GET})
	public String getTicket(ModelMap model, @PathVariable int itemId)
	{
		WheelTicketPrice price = CmsApplication.getWheelTicketPrices().get(itemId);
		if(price == null)
		{
			return show(model);
		}

		MAccount mAccount = (MAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(itemService.removeUserItem(mAccount.getId(), price.getItemId(), price.getItemCount(), "Покупка билетов для Колеса Удачи"))
		{
			mAccount.setWheelTicket(mAccount.getWheelTicket() + 1);
			mAccountService.save(mAccount);
		}

		return show(model);
	}

	@RequestMapping(value = "/getItem", method = {RequestMethod.GET})
	public
	@ResponseBody
	Wheel getItem()
	{
		MAccount mAccount = (MAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Wheel wheel = wheelService.findRandom();
		if(wheelService.findAll().isEmpty() || mAccount.getWheelTicket() < 1 || wheel == null)
		{
			return null;
		}
		mAccount.setWheelTicket(mAccount.getWheelTicket() - 1);
		mAccountService.save(mAccount);

		wheel.setItemName(itemNameService.getItemName(wheel.getItemId()));
		itemService.addUserItem(mAccount.getId(), wheel.getItemId(), wheel.getItemCount(), "Колесо Удачи");
		return wheel;
	}
}