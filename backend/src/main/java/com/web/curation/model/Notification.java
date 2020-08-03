package com.web.curation.model;

import java.util.Date;

public class Notification {
	private int id;
	private int follower_id;
	private int followee_id;
	private int newtofollower;
	private int newtofollowee;
	private int approval;
	private Date time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getNewtofollower() {
		return newtofollower;
	}
	public void setNewtofollower(int newtofollower) {
		this.newtofollower = newtofollower;
	}
	public int getNewtofollowee() {
		return newtofollowee;
	}
	public void setNewtofollowee(int newtofollowee) {
		this.newtofollowee = newtofollowee;
	}
	public int getApproval() {
		return approval;
	}
	public void setApproval(int approval) {
		this.approval = approval;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	public int getFollower_id() {
		return follower_id;
	}
	public void setFollower_id(int follower_id) {
		this.follower_id = follower_id;
	}
	public int getFollowee_id() {
		return followee_id;
	}
	public void setFollowee_id(int followee_id) {
		this.followee_id = followee_id;
	}
	
	
	public Notification(int id, int follower_id, int followee_id, int newtofollower, int newtofollowee, int approval,
			Date time) {
		super();
		this.id = id;
		this.follower_id = follower_id;
		this.followee_id = followee_id;
		this.newtofollower = newtofollower;
		this.newtofollowee = newtofollowee;
		this.approval = approval;
		this.time = time;
	}
	public Notification() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Notification [id=" + id + ", follower_id=" + follower_id + ", followee_id=" + followee_id
				+ ", newtofollower=" + newtofollower + ", newtofollowee=" + newtofollowee + ", approval=" + approval
				+ ", time=" + time + "]";
	}
	
	
}
