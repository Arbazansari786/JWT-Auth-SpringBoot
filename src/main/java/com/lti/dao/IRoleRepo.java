package com.lti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.entity.Role;

@Repository
public interface IRoleRepo extends JpaRepository<Role, String>{

}
