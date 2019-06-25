package com.osewald.springrest.h2.model;

public class Timestamp {
	private int hours; 
	private int minutes;
	
	
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	public Timestamp() {
		
	}
	
	public Timestamp(int hrs, int mns) {
		this.hours = hrs;
		this.minutes = mns;
	}
	@Override
	public String toString() {
		return this.hours + ":" + this.minutes;
	}
	
	
	
}
