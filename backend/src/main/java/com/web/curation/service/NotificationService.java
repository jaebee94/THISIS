package com.web.curation.service;

import java.util.List;

import com.web.curation.model.Notification;

public interface NotificationService {
	public List<Notification> selectNotification();
	public Notification selectNotificationById(String id);
	public int insertNotification(Notification notification);
	public int updateNotification(Notification notification);
	public int deleteNotification(String follower_id, String followee_id);
	public List<Notification> GetListByFollower(int user_id);
	public List<Notification> GetListByFollowee(int user_id);
}
