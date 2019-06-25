package com.osewald.springrest.h2.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.osewald.springrest.h2.model.DailyEvent;
import com.osewald.springrest.h2.model.Workday;

public interface DailyEventRepository extends CrudRepository<DailyEvent, Long> {
	List<DailyEvent> findByWorkday(Workday workday);
	void deleteAllByWorkday(Workday workday);
}
