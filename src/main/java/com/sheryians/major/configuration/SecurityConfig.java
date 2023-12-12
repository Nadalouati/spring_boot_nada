package com.sheryians.major.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sheryians.major.service.CostumUserDetailService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
	@Autowired
	CostumUserDetailService costumUserDetailService;
	
	@Override
	protected void confiqure(HttpSecurity http) throws Exception{
		http
				.authorizeRequests()
				.antMatchers("/","/shop/**","/register","/h2-console/**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.failureUrl("/")
				.usernameParameter("email")
				.passwordParameter("password")
				.and()
				.oauthLogin()
				.loginPage("login")
				.successHandler(googleOAuth2SuccessHandler)
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.and()
				.exceptionHandling()
				.and()
				.csrf()
				.disable();
		http.headers().frameOptions().disable();
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(costumUserDetailService);
	}
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/resources/**","/static/**","/images/**","/productimages/**","/css/**","/js/**");
	}
}
