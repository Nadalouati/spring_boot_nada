/**package com.sheryians.major.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ObservationAuthenticationManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sheryians.major.model.Role;
import com.sheryians.major.model.User;
import com.sheryians.major.repository.RoleRepository;
import com.sheryians.major.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler{
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	private RedirectStrategy  redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse ,Authentication authentication) throws IOException {
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)authentication;
		String email = token.getPrincipal().getAttributes().get("email").toString();
		if (userRepository.findUserByEmail(email).isPresent()) {
			
		}else {
			User user = new User();
			user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
			user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
			user.setEmail(email);
			List<Role> roles= new ArrayList<>();
			roles.add(roleRepository.findById(2).get());
			userRepository.save(user);
		}
		redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse,"/");
	}
}*/
