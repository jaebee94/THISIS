package com.web.curation.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.web.curation.model.user.User;

@Repository
@Mapper
public interface UserMapper {
	public List<User> findAll();
	
}
