package studio.lineage2.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import studio.lineage2.cms.model.Info;
import studio.lineage2.cms.service.InfoService;

import java.util.List;

/**
 Eanseen
 29.05.2016
 */
@Controller @RequestMapping("/") public class SiteController
{
	@Value("${template}") private String template;
	@Autowired private InfoService infoService;

	private void addAttributes(ModelMap model, String page)
	{
		model.addAttribute("header", template + "/header.vm");
		model.addAttribute("body", template + "/body.vm");
		model.addAttribute("footer", template + "/footer.vm");

		model.addAttribute("page", page);

		List<Info> infos = infoService.findAll();
		infos.sort((o1, o2)->(int) (o2.getId() - o1.getId()));
		model.addAttribute("infos", infos);
	}

	@RequestMapping(method = {
			RequestMethod.GET,
			RequestMethod.HEAD
	})
	public String index(ModelMap model)
	{
		addAttributes(model, template + "/index.vm");
		return "main";
	}

	@RequestMapping(value = "/page/{page}", method = {RequestMethod.GET})
	public String page(ModelMap model, @PathVariable String page)
	{
		addAttributes(model, template + "/pages/" + page + ".vm");
		return "main";
	}
}