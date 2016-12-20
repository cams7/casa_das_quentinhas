package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.model.UserProfile;

public interface UserProfileDao {

	List<UserProfile> findAll();

	UserProfile findByType(String type);

	UserProfile findById(int id);
}
