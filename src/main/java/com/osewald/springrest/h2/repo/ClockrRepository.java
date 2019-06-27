package com.osewald.springrest.h2.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.osewald.springrest.h2.model.Clockr;
import com.osewald.springrest.h2.model.User;

public interface ClockrRepository extends CrudRepository<Clockr, Long> {
	
	@Query("SELECT c from Clockr c WHERE c.user =:user")
	List<Clockr> findByUserCustom(@Param("user")User user);
	
	@Query(value = "SELECT * FROM Clockr", nativeQuery = true)
	List<Clockr> findAllCustom();
	
	@Modifying
	@Transactional
	@Query(value = "insert into Clockr (id, message, time, user_id) VALUES (hibernate_sequence.nextval, :message, :time, :user) ", nativeQuery = true)
	void postClockrCustom(@Param("message")String message, @Param("time") Date time, @Param("user") Long usrId);
	
	
	List<Clockr> findByUser(User user);
}
