package com.sheryians.major.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheryians.major.model.User;

public interface RoleRepository extends JpaRepository<User, Integer>{

}
