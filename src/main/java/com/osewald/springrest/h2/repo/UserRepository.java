package com.osewald.springrest.h2.repo;

import org.springframework.data.repository.CrudRepository;

import com.osewald.springrest.h2.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}