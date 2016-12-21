package studio.lineage2.cms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import studio.lineage2.cms.service.MAccountService;

/**
 Eanseen
 27.05.2016
 */
@Configuration @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER) public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired private MAccountService mAccountService;

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.
				csrf().disable().
				authorizeRequests().
				antMatchers("/", "/page/*").permitAll().
				antMatchers("/**/css/**", "/**/fonts/**", "/**/js/**", "/**/images/**", "/**/img/**").permitAll().
				antMatchers("/image/**").permitAll().
				antMatchers("/pay/check*", "/api/*").permitAll().
				antMatchers("/XmlRpcGame/getTopPvP", "/XmlRpcGame/getTopPK").permitAll().
				antMatchers("/enter", "/enter/reg", "/enter/auth", "/enter/recover").permitAll().
				antMatchers("/admin/**").hasRole("ADMIN").
				anyRequest().hasRole("USER").and().
				formLogin().loginPage("/enter");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(mAccountService::findByUsername).passwordEncoder(new BCryptPasswordEncoder());
	}
}