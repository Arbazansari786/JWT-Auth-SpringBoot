package com.lti.service;


import com.lti.dao.IRoleRepo;
import com.lti.dao.IUserRepo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lti.entity.Role;
import com.lti.entity.User;

@Service
public class UserService {

	@Autowired
	IUserRepo repo;
	
	@Autowired
	IRoleRepo repoRole;
	@Autowired
	PasswordEncoder encoder;
	
	public User createNewUser(User user) {
		Role role = repoRole.findById("user").get();
		System.out.println(role.getRoleName());
		Set<Role> set = new HashSet();
		set.add(role);
		user.setRoles(set);
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}
	
}
