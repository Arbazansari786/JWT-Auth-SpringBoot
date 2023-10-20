package com.lti.restcontroller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.entity.Role;
import com.lti.service.RoleService;

@RestController
@RequestMapping("api/role")
public class RoleController {
	
	@Autowired
	RoleService service;
	
	@PostConstruct
	public void initRoleAndUser() {
		service.initRoleAndUser();
	}
	
	@PostMapping("/createNewRole")
	public ResponseEntity<Role> createNewRole(@RequestBody Role role) {
		Role savedRole = service.createNewRole(role);
		return new ResponseEntity<Role>(savedRole,HttpStatus.OK);
	}
	

}
