package com.osewald.springrest.h2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.osewald.springrest.h2.model.User;
import com.osewald.springrest.h2.repo.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository repository;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		System.out.println("Get all Users...");

		List<User> users = new ArrayList<>();
		repository.findAll().forEach(users::add);

		return users;
	}

	@GetMapping("/users/username/{username}")
	public User getUserByUsername(@PathVariable String username) {
		User user = repository.findByUsername(username);
		return user;
	}

	@PostMapping(value = "/users/login")
	public ResponseEntity<User> tryLogIn(@RequestBody User user) {
		System.out.println("Trying to log in User: " + user.getUsername());

		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
			System.out.println("Login Success!");
			//User usr = repository.findByUsername(user.getUsername());

			User usr = repository.findByUsernameCustom(user.getUsername());

			System.out.println(usr.getUsername() + " with Role: " + usr.getUserRolle() + " has just logged in!");

			return ResponseEntity.ok(usr);
		} catch (Exception e) {
			System.out.println("Login Failed!");
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}

	@PostMapping(value = "/users/create")
	public User postUser(@RequestBody User newUser) {

		/*
		User _user = repository.save(new User(newUser.getUsername(), newUser.getPassword(), newUser.getUserRolle(),
				newUser.getAnrede(), newUser.getHandle(), newUser.getWorkStartClockrMessage(),
				newUser.getBreakStartClockrMessage(), newUser.getBreakEndClockrMessage(),
				newUser.getWorkEndClockrMessage(), newUser.getProfilePicture()));
		*/
		repository.postUserCustom(newUser.getUsername(), newUser.getPassword(), newUser.getUserRolle().toString(), newUser.getProfilePicture(), newUser.getWorkStartClockrMessage(), newUser.getBreakStartClockrMessage(), newUser.getBreakEndClockrMessage(), newUser.getWorkEndClockrMessage(), newUser.getVorname(), newUser.getNachname(), newUser.getAnrede(), newUser.getHandle());
		
		
		User _user = new User(newUser.getUsername(), newUser.getPassword(), newUser.getUserRolle(),
				newUser.getAnrede(), newUser.getHandle(), newUser.getWorkStartClockrMessage(),
				newUser.getBreakStartClockrMessage(), newUser.getBreakEndClockrMessage(),
				newUser.getWorkEndClockrMessage(), newUser.getProfilePicture());
		return _user;
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		System.out.println("Update User with ID = " + id + "...");

		//Optional<User> userData = repository.findById(id);
		Optional<User> userData = repository.findByIdCustom(id);
		
		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setVorname(user.getVorname());
			_user.setNachname(user.getNachname());
			_user.setUserRolle(user.getUserRolle());
			_user.setProfilePicture(user.getProfilePicture());
			_user.setWorkStartClockrMessage(user.getWorkStartClockrMessage());
			_user.setBreakStartClockrMessage(user.getBreakStartClockrMessage());
			_user.setBreakEndClockrMessage(user.getBreakEndClockrMessage());
			_user.setWorkEndClockrMessage(user.getWorkEndClockrMessage());
			_user.setAnrede(user.getAnrede());
			_user.setHandle(user.getHandle());
			_user.setId(user.getId());
			
			//return new ResponseEntity<>(repository.save(_user), HttpStatus.OK);
			repository.updateUserCustom(_user.getId(), _user.getUsername(), _user.getPassword(), _user.getUserRolle().toString(), _user.getProfilePicture(), _user.getWorkStartClockrMessage(), _user.getBreakStartClockrMessage(), _user.getBreakEndClockrMessage(), _user.getWorkEndClockrMessage(), _user.getVorname(), _user.getNachname(), _user.getAnrede(), _user.getHandle());
			
			return new ResponseEntity<>(_user, HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
