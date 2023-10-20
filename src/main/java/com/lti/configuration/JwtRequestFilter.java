package com.lti.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lti.service.JwtService;
import com.lti.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String tokenHeader = request.getHeader("Authorization");
		String jwtToken=null;
		String userName=null;
		System.out.println(tokenHeader);
		if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
			jwtToken = tokenHeader.substring(7);
			try {
				 userName = jwtUtil.getUserNameFromToken(jwtToken);
			}catch (IllegalArgumentException e) {
				// TODO: handle exception
				System.out.println("Unable to get Jwt Token");
			}catch (ExpiredJwtException e) {
				// TODO: handle exception
				System.out.println("Jwt has been Expires");
				
			}
			
		}
		else {
			System.out.println("Jwt Token doesn't start with bearer");
		}
		
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails = jwtService.loadUserByUsername(userName);
			if(jwtUtil.validateTokens(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			}
			
		}
		
		filterChain.doFilter(request, response);
		
	}

}
