package com.web.curation.model;

public class Follow {
	private int followee;
	private int follower;
	public int getFollowee() {
		return followee;
	}
	public void setFollowee(int followee) {
		this.followee = followee;
	}
	public int getFollower() {
		return follower;
	}
	public void setFollower(int follower) {
		this.follower = follower;
	}
	@Override
	public String toString() {
		return "Follow [followee=" + followee + ", follower=" + follower + "]";
	}
	
	
}
