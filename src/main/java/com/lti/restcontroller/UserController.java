package com.lti.restcontroller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.entity.User;
import com.lti.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	
   @Autowired
   UserService service;
   
  
   
   @PostMapping("/createNewUser")
   public User createNewUser(@RequestBody User user) {
	   
	   System.out.println("Crete New User");
	   
	   User savedUser = service.createNewUser(user);
	   
	   return savedUser;
   }
   
   @GetMapping("/foruser")
   @PreAuthorize("hasRole('user')")
   public ResponseEntity<String> forUser(){
	   return new ResponseEntity<String>("This is for uSer only",HttpStatus.OK);
   }
   
   @GetMapping("/foradmin")
   @PreAuthorize("hasRole('admin')")
   public ResponseEntity<String> forAdmin(){
	   return new ResponseEntity<String>("This is for admin only",HttpStatus.OK);
   }



}
