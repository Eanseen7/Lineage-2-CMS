package studio.lineage2.cms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import studio.lineage2.cms.interceptor.SiteInterceptor;

import java.io.File;
import java.util.Locale;

/**
 Eanseen
 27.05.2016
 */
@Configuration @PropertySource(value = "file:./application.properties", ignoreResourceNotFound = true) @PropertySource(value = "file:./public/application.properties", ignoreResourceNotFound = true) public class WebConfig extends WebMvcConfigurerAdapter
{
	@Autowired private SiteInterceptor siteInterceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		String currentPath = new File(".").getAbsolutePath();
		String location = "file:///" + currentPath + "/public/";
		registry.addResourceHandler("/**").addResourceLocations(location);
	}

	@Bean(name = "localeResolver")
	public LocaleResolver localeResolver()
	{
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("ru"));
		resolver.setCookieName("lang");
		resolver.setCookieMaxAge(Integer.MAX_VALUE);
		return resolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
		registry.addInterceptor(siteInterceptor);
	}
}