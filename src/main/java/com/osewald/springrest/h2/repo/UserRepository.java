package com.osewald.springrest.h2.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.osewald.springrest.h2.model.User;
import com.osewald.springrest.h2.model.UserRole;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	
	@Query("Select usr from User usr where usr.username = :username")
	User findByUsernameCustom(@Param("username") String username);

	@Modifying
	@Transactional
	@Query(value = "update User c SET c.username = :usr, c.password = :pwd, c.userRolle = :rl, c.profilePicture = :pp, c.workStartClockrMessage = :ws, c.breakStartClockrMessage = :bs,  c.breakEndClockrMessage = :be, c.workEndClockrMessage = :we, c.vorname = :vor, c.nachname = :nach, c.anrede = :an, c.handle = :hnd WHERE c.id = :id ")
	void updateUserCustom(@Param("id") Long id, @Param("usr") String usr,  @Param("pwd") String pw,  @Param("rl") UserRole rl,  @Param("pp") String pp,  @Param("ws")String wd,  @Param("bs") String bs,   @Param("be") String be,  @Param("we") String we,  @Param("vor") String vor,  @Param("nach") String nach,  @Param("an") String an,  @Param("hnd") String hnd);
	
	
	@Query("SELECT usr FROM User usr WHERE usr.id=:id")
	Optional<User> findByIdCustom(@Param("id") Long id);
}