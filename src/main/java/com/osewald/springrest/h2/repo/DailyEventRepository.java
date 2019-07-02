package com.osewald.springrest.h2.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.osewald.springrest.h2.model.DailyEvent;
import com.osewald.springrest.h2.model.ProgramState;
import com.osewald.springrest.h2.model.Timestamp;
import com.osewald.springrest.h2.model.Workday;

public interface DailyEventRepository extends CrudRepository<DailyEvent, Long> {
	List<DailyEvent> findByWorkday(Workday workday);
	void deleteAllByWorkday(Workday workday);
	
	@Query("SELECT de FROM DailyEvent de WHERE de.workday=:workday")
	List<DailyEvent> findByWorkdayCustom(@Param("workday") Workday workday);
	
	
	@Modifying
	@Transactional
	@Query(value = "delete from DailyEvent de where de.id = :id ")
	void deleteDailyEventCustom(@Param("id")Long id);
	
	@Modifying
	@Transactional
	@Query(value = "delete from DailyEvent de where de.workday = :wd ")
	void deleteAllByWorkdayCustom(@Param("wd")Workday workday);
}
