package com.web.curation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dao.NotificationDAO;
import com.web.curation.model.Notification;

@Service
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	private NotificationDAO notificationDao;
	
	@Override
	public List<Notification> selectNotification() {
		return notificationDao.selectNotification();
	}

	@Override
	public Notification selectNotificationById(String id) {
		return notificationDao.selectNotificationById(id);
	}

	@Override
	public int insertNotification(Notification notification) {
		return notificationDao.insertNotification(notification);
	}

	@Override
	public int updateNotification(Notification notification) {
		return notificationDao.updateNotification(notification);
	}

	@Override
	public int deleteNotification(String follower_id, String followee_id) {
		return notificationDao.deleteNotification(follower_id, followee_id);
	}

	@Override
	public List<Notification> GetListByFollower(int user_id) {
		return notificationDao.GetListByFollower(user_id);
	}

	@Override
	public List<Notification> GetListByFollowee(int user_id) {
		return notificationDao.GetListByFollowee(user_id);
	}
	
}
