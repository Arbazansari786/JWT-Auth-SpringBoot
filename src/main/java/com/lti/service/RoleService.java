package com.lti.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lti.dao.IRoleRepo;
import com.lti.dao.IUserRepo;
import com.lti.entity.Role;
import com.lti.entity.User;

@Service
public class RoleService {

	@Autowired
	IRoleRepo repo;
	
	@Autowired
	IUserRepo repoUser;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	public Role createNewRole(Role role) {
		return repo.save(role);
	}
		
	public void initRoleAndUser() {
		Role role1 = new Role("admin","Admin Roles ");
		Role role2=new Role("user","Default User Roles");
		repo.save(role1);
		repo.save(role2);
		
		User user = new User();
		user.setFirstName("admin");
		user.setLastName("bhai");
		user.setEmail("Admin001@gmail.com");
		user.setPassword(getEncodePassword("Admin123"));
		user.setUserName("admin01");
		Set<Role> set1 = new HashSet<>();
		set1.add(role1);
		user.setRoles(set1);
		repoUser.save(user);
		
//		User user2 = new User();
//		user2.setFirstName("Arbaz");
//		user2.setLastName("Ansari");
//		user2.setEmail("ansariarbaz720@gmail.com");
//		user2.setPassword(getEncodePassword("Arbaz7738"));
//		user2.setUserName("arbaz7738");
//		Set<Role> set2 = new HashSet<>();
//		set2.add(role2);
//		user2.setRoles(set2);
//		repoUser.save(user2);
		
	}
	
	private String getEncodePassword(String password){
		
		return passwordEncoder.encode(password);
		
	}
	
}
