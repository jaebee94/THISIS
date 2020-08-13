package com.web.curation.model;

import io.swagger.annotations.ApiModelProperty;

public class UserResponse {
	@ApiModelProperty(value = "status", position = 1)
	public boolean status;
	@ApiModelProperty(value = "data", position = 2)
	public String data;
	public String accessToken;
	public int user_id;
	public String username;
	public String introduction;
	public String email;
	public String nickname;
	public String userimage;
	public String role;
	public int disabled;
	public int subscribeCount;  
}
