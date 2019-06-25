package com.osewald.springrest.h2.model;

public class DailyEventDto {
	private DailyEvent event;
	private Workday workday;
	
	public DailyEventDto() {
		
	}
	
	public DailyEventDto(DailyEvent evt, Workday wd) {
		this.event = evt; 
		this.workday = wd;
	}

	public DailyEvent getEvent() {
		return event;
	}

	public void setEvent(DailyEvent event) {
		this.event = event;
	}

	public Workday getWorkday() {
		return workday;
	}

	public void setWorkday(Workday workday) {
		this.workday = workday;
	}
	
	
}
