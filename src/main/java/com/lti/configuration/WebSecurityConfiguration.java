package com.lti.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lti.service.JwtService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
//    @Bean
//    public AuthenticationProvider authenticationProvider() { 
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
//        authenticationProvider.setUserDetailsService(UserDetailsService()); 
//        authenticationProvider.setPasswordEncoder(passwordEncoder()); 
//        return authenticationProvider; 
//    } 
//  
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
        return config.getAuthenticationManager(); 
    } 
		
	@Bean
	public SecurityFilterChain securityWeb(HttpSecurity http) throws Exception {
		System.out.println("Security Filter Chain");
		http.cors();
		 return http.csrf().disable()
				.authorizeHttpRequests().antMatchers("/api/user/createNewUser","/authenticate").permitAll()
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
				and().
				 addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				 .build();
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configurableGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    	authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
    }

	
	
}
