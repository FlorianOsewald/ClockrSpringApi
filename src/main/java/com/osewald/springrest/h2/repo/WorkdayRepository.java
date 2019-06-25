package com.osewald.springrest.h2.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.osewald.springrest.h2.model.Workday;

public interface WorkdayRepository extends CrudRepository<Workday, Long> {
	List<Workday> findByUsername(String username);
	
	@Query("SELECT wd FROM Workday wd WHERE wd.date = TODAY and wd.username=:username")
	Workday getWorkdayTodayUsername(@Param("username") String username);
}
