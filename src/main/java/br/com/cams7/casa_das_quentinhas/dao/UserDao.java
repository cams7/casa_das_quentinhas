package br.com.cams7.casa_das_quentinhas.dao;

import java.util.List;

import br.com.cams7.casa_das_quentinhas.model.User;

public interface UserDao {

	User findById(int id);

	User findBySSO(String sso);

	void save(User user);

	void deleteBySSO(String sso);

	List<User> findAllUsers();

}
