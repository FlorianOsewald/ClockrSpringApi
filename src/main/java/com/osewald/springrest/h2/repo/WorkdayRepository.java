package com.osewald.springrest.h2.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.osewald.springrest.h2.model.Workday;

public interface WorkdayRepository extends CrudRepository<Workday, Long> {
	List<Workday> findByUsername(String username);
	
	@Query("SELECT wd FROM Workday wd WHERE wd.date = TODAY and wd.username=:username")
	Workday getWorkdayTodayUsername(@Param("username") String username);
	
	@Query("SELECT wd FROM Workday wd WHERE wd.username=:username")
	List<Workday> findByUsernameCustom(@Param("username") String username);
	
	@Query("SELECT wd FROM Workday wd WHERE wd.id=:id")
	Optional<Workday> findByIdCustom(@Param("id") Long id);
	

	@Modifying
	@Transactional
	@Query(value = "UPDATE Workday wd SET wd.date = :date, wd.username = :user where wd.id = :id")
	void updateWorkdayCustom(@Param("id")long id, @Param("date")Date date, @Param("user") String username);
	
	
	@Modifying
	@Transactional
	@Query(value = "delete from Workday wd where wd.id = :id ")
	void deleteWorkdayCustom(@Param("id")Long id);
}
