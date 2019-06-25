package com.osewald.springrest.h2.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "username", unique=true)
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "userRolle")
	private UserRole userRolle;
	
	@Column(name = "profilePicture")
	private String profilePicture;
	
	@Column(name = "workStartClockrMessage")
	private String workStartClockrMessage;
	
	@Column(name = "breakStartClockrMessage")
	private String breakStartClockrMessage;
	
	@Column(name = "breakEndClockrMessage")
	private String breakEndClockrMessage;
	
	@Column(name = "workEndClockrMessage")
	private String workEndClockrMessage;
	
	@Column(name = "vorname")
	private String vorname;
	
	@Column(name = "nachname")
	private String nachname;
	
	@Column(name = "anrede")
	private String anrede;
	
	@Column(name = "handle")
	private String handle;
	
	/*
	@OneToMany(mappedBy = "user")
	private List<Clockr> clockrs; */

	public User() {
	}

	public User(String usr, String pw, UserRole rolle) {
		this.username = usr;
		this.password = pw;
		this.userRolle = rolle;
	}
	
	public User(String usr, String pw, UserRole rolle, String anrede, String handle, 
			String wsCl, String bsCl, String beCl, String weCl, String pp) {
		this.username = usr;
		this.password = pw;
		this.userRolle = rolle;
		this.anrede = anrede;
		this.handle = handle;
		this.workStartClockrMessage = wsCl;
		this.breakStartClockrMessage = bsCl;
		this.breakEndClockrMessage = beCl;
		this.workEndClockrMessage = weCl;
		this.profilePicture = pp;
	}
	
	/*
	public List<Clockr> getClockrs() {
		return clockrs;
	}
	
	public void setClockrs(List<Clockr> clockrs) {
		this.clockrs = clockrs;
	} */
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}




	public void setUsername(String username) {
		this.username = username;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public UserRole getUserRolle() {
		return userRolle;
	}




	public void setUserRolle(UserRole userRolle) {
		this.userRolle = userRolle;
	}




	public String getProfilePicture() {
		return profilePicture;
	}




	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}




	public String getWorkStartClockrMessage() {
		return workStartClockrMessage;
	}




	public void setWorkStartClockrMessage(String workStartClockrMessage) {
		this.workStartClockrMessage = workStartClockrMessage;
	}




	public String getBreakStartClockrMessage() {
		return breakStartClockrMessage;
	}




	public void setBreakStartClockrMessage(String breakStartClockrMessage) {
		this.breakStartClockrMessage = breakStartClockrMessage;
	}




	public String getBreakEndClockrMessage() {
		return breakEndClockrMessage;
	}




	public void setBreakEndClockrMessage(String breakEndClockrMessage) {
		this.breakEndClockrMessage = breakEndClockrMessage;
	}




	public String getWorkEndClockrMessage() {
		return workEndClockrMessage;
	}




	public void setWorkEndClockrMessage(String workEndClockrMessage) {
		this.workEndClockrMessage = workEndClockrMessage;
	}




	public String getVorname() {
		return vorname;
	}




	public void setVorname(String vorname) {
		this.vorname = vorname;
	}




	public String getNachname() {
		return nachname;
	}




	public void setNachname(String nachname) {
		this.nachname = nachname;
	}




	public String getAnrede() {
		return anrede;
	}




	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}




	public String getHandle() {
		return handle;
	}




	public void setHandle(String handle) {
		this.handle = handle;
	}




	@Override
	public String toString() {
		return vorname + " " + nachname;
	}
}