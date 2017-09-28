/**
 * 
 */
package com.tinnguyen263.mykanban.service.impl;

import java.util.List;

import com.tinnguyen263.mykanban.model.User;
import com.tinnguyen263.mykanban.repository.UserRepository;
import com.tinnguyen263.mykanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User getByKey(String username) {
		return userRepository.findOne(username);
	}

	@Override
	public User getByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public User saveOrUpdate(User user) {
		return userRepository.save(user);
	}

	@Override
	public boolean deleteUser(String username) {
		try {
			userRepository.delete(username);
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}
}
