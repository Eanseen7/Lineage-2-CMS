package studio.lineage2.cms.controller.cp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.lineage2.cms.CmsApplication;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.service.ItemNameService;
import studio.lineage2.cms.service.ItemService;
import studio.lineage2.cms.xml.ShopPrice;

/**
 Eanseen
 29.06.2016
 */
@Controller @RequestMapping("/shop") public class CpShopController
{
	@Autowired private ItemNameService itemNameService;
	@Autowired private ItemService itemService;

	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String show(ModelMap model)
	{
		model.addAttribute("itemNames", itemNameService);
		model.addAttribute("itemService", itemService);
		model.addAttribute("prices", CmsApplication.getShopPrices().values());
		model.addAttribute("page", "cp/user/shop.vm");
		return "cp/index";
	}

	@RequestMapping(value = "/buy", method = {RequestMethod.POST}, produces = {"application/text; charset=UTF-8"})
	public
	@ResponseBody
	String buy(int shopPriceId, int itemCount)
	{
		if(itemCount < 1 || itemCount > 5000)
		{
			return "<div class=\"uk-alert uk-alert-danger\">Кол-во должно быть от 1 до 5000</div>";
		}

		ShopPrice shopPrice = CmsApplication.getShopPrices().get(shopPriceId);
		if(shopPrice == null)
		{
			return "<div class=\"uk-alert uk-alert-danger\">Что-то пошло не так</div>";
		}

		MAccount mAccount = (MAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(itemService.removeUserItem(mAccount.getId(), shopPrice.getPriceItemId(), shopPrice.getPriceItemCount() * itemCount, "Покупка " + itemNameService.getItemName(shopPrice.getItemId())))
		{
			itemService.addUserItem(mAccount.getId(), shopPrice.getItemId(), itemCount, "Покупка " + itemNameService.getItemName(shopPrice.getItemId()));
			return "<div class=\"uk-alert uk-alert-success\">Предмет успешно куплен, Вы можете его найти в разделе <a href=/cp/item target=_blank>Все предметы</a></div>";
		}
		else
		{
			return "<div class=\"uk-alert uk-alert-danger\">Нехватает средств</div>";
		}
	}
}