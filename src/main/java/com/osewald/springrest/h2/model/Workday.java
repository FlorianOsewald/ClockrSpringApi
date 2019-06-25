package com.osewald.springrest.h2.model;

import java.sql.Date;
import java.util.ArrayList;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "workday")
public class Workday {
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long id;
		
		@Column(name = "date", nullable = false)
		private Date date;
		
		@Column(name = "username", nullable = false)
		private String username;
		
		
		@OneToMany(mappedBy = "workday")
		@JsonManagedReference
		private List<DailyEvent> dailyEvents;
		
		public Workday() {
		}
		
		public Workday(int id) {
			this.id = id;
		}
		public Workday(Date time, String usr) {
			this.date = time;
			this.username = usr;
			this.dailyEvents = new ArrayList<DailyEvent>();
		}
		
		public Workday(Date time, String usr, List<DailyEvent> events) {
			System.out.println("creating Workday by all 3 Attributes");
			this.date = time;
			this.username = usr;
			this.dailyEvents = new ArrayList<DailyEvent>();
		}
	
		public List<DailyEvent> getDailyEvents() {
			return dailyEvents;
		}



		public void setDailyEvents(List<DailyEvent> dailyEvents) {
			this.dailyEvents = dailyEvents;
		}



		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
		
		
}
