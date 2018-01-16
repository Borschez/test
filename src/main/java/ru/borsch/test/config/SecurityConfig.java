package ru.borsch.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Value("${security.encoding-strength}")
	private Integer encodingStrength;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
				.httpBasic().and()
				.authorizeRequests()
				.antMatchers("/index", "/home.html", "/login.html", "/app.html", "/", "/home", "/login","/css/**","/js/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.logout()
				.logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID", "SESSION")
                //.anyRequest().permitAll()
				.and()
				.csrf()
				.disable();
				//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		// @formatter:on
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(new ShaPasswordEncoder(encodingStrength));
	}

}
