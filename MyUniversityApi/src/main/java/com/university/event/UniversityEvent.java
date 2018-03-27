package com.university.event;

import java.sql.Timestamp;

public class UniversityEvent {

	private String eventId;
	private String eventType;
	private String eventDescription;
	private String userId;
	private Timestamp currentTimeStamp;
	private String roleName;
	
	
	public UniversityEvent() {
		super();
	}
	
	public UniversityEvent(String eventId, String eventType, String eventDescription, String userId,
			Timestamp currentTimeStamp, String roleName) {
		super();
		this.eventId = eventId;
		this.eventType = eventType;
		this.eventDescription = eventDescription;
		this.userId = userId;
		this.currentTimeStamp = currentTimeStamp;
		this.roleName = roleName;
	}

	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getCurrentTimeStamp() {
		return currentTimeStamp;
	}
	public void setCurrentTimeStamp(Timestamp currentTimeStamp) {
		this.currentTimeStamp = currentTimeStamp;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "[eventId=" + eventId + ", eventType=" + eventType + ", eventDescription="
				+ eventDescription + ", userId=" + userId + ", currentTimeStamp=" + currentTimeStamp + ", roleName="
				+ roleName + "]";
	}

}
