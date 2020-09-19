package com.web.curation.model;

public class NotificationResponse {
	private Notification notification;
	private UserInfo userInfo;
	
	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public String toString() {
		return "NotificationResponse [notification=" + notification + ", userInfo=" + userInfo + "]";
	}
	
}
