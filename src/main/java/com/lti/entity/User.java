package com.lti.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
		
	@Id
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE",
	joinColumns= {
			@JoinColumn(name="USER_ID")
			
	},
	inverseJoinColumns={
		@JoinColumn(name="ROLE_ID")
	}
	)
	private Set<Role> roles;
	
}
