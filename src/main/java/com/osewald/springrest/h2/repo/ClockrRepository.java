package com.osewald.springrest.h2.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.osewald.springrest.h2.model.Clockr;
import com.osewald.springrest.h2.model.User;

public interface ClockrRepository extends CrudRepository<Clockr, Long> {
	
	List<Clockr> findByUser(User user);

}
