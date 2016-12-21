package studio.lineage2.cms.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.lineage2.cms.model.IMessage;
import studio.lineage2.cms.model.MAccount;
import studio.lineage2.cms.service.ItemService;
import studio.lineage2.cms.service.MAccountService;

/**
 Eanseen
 08.06.2016
 */
@Controller @RequestMapping("/admin/manager") public class AdminManagerController
{
	@Autowired private MAccountService mAccountService;

	@Autowired private ItemService itemService;

	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String index(ModelMap model)
	{
		AdminController.addAttributes(model, "cp/admin/manager/index.vm", "cp/admin/menu_manager.vm");
		return "cp/index";
	}

	@RequestMapping(value = "/item", method = {RequestMethod.GET})
	public String item(ModelMap model)
	{
		AdminController.addAttributes(model, "cp/admin/manager/item.vm", "cp/admin/menu_manager.vm");
		return "cp/index";
	}

	@RequestMapping(value = "/addItem", method = {RequestMethod.POST})
	public
	@ResponseBody
	IMessage addItem(String mAccountId, String itemId, String itemCount)
	{
		MAccount mAccount = mAccountService.findOne(Long.parseLong(mAccountId));

		if(mAccount == null)
		{
			return new IMessage(IMessage.Type.FAIL, "Что-то пошло не так");
		}

		return itemService.addUserItem(mAccountId, itemId, itemCount, "Начислено Администратором");
	}
}