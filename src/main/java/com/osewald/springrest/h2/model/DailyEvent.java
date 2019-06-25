package com.osewald.springrest.h2.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.ManyToOne;

@Entity
@Table(name = "dailyEvent")
public class DailyEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Columns(columns = { @Column(name = "hours"), @Column(name = "minutes") })
	@Type(type = "com.osewald.springrest.h2.type.TimeStampType")
	private Timestamp time;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "eventType")
	private ProgramState eventType;
	
	@ManyToOne
	@JoinColumn(name = "workday_id")
	@JsonBackReference
    private Workday workday;

	public DailyEvent(ProgramState t, Timestamp ti, Workday wd) {
		this.eventType = t;
		this.time = ti;
		this.workday = wd;
	}
	
	public DailyEvent() {
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public ProgramState getEventType() {
		return eventType;
	}

	public void setEventType(ProgramState eventType) {
		this.eventType = eventType;
	}

	public Workday getWorkday() {
		return workday;
	}

	public void setWorkday(Workday workday) {
		this.workday = workday;
	}
	
	
}
