package com.lti.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lti.dao.IUserRepo;
import com.lti.entity.JwtRequest;
import com.lti.entity.JwtResponse;
import com.lti.entity.Role;
import com.lti.entity.User;
import com.lti.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	IUserRepo userRepo;

	@Autowired
	AuthenticationManager authenticationManager;

	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String userName = jwtRequest.getUserName();
		String userPassword = jwtRequest.getUserPassword();
		authenticate(userName, userPassword);
		
		UserDetails userDetails = loadUserByUsername(userName);
		String generateToken = jwtUtil.generateToken(userDetails);
		User user = userRepo.findById(userName).get();
		JwtResponse jwtResponse = new JwtResponse(user, generateToken);
		return jwtResponse;

	}

	private void authenticate(String userName, String userPassword) throws Exception{
		// TODO Auto-generated method stub

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException e) {
			throw new Exception("USER DISABLED", e);
		} catch (BadCredentialsException e) {

			throw new Exception("INVALID CREDENTIAL", e);
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepo.findById(username).get();

		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
					getAuthority(user));
		} else {
			throw new UsernameNotFoundException("User not fount with usename as :" + username);
		}

	}

	private Set getAuthority(User user) {


		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();

		Set<Role> roles = user.getRoles();

		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});
		return authorities;

	}

}
