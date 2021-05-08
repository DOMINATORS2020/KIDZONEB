package tn.esprit.kidzone.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.kidzone.entity.User;
import tn.esprit.kidzone.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService{
	private static final Logger l = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository repo;
	@Autowired
	UserServiceImpl userService;

	@Override
	public List<User> retrieveAllUsers() {
		List<User> list = (List<User>) repo.findAll();
		for (User user : list) {
			 l.info("" + user);
			 
		}
		return list;
	}

	@Override
	public User addUser(User u) {
		repo.save(u);
		return u;
	}

	@Override
	public void deleteUser(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User updateUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User retrieveUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User authenticate(String login, String password) {
		// TODO Auto-generated method stub
		return repo.getUserByLoginAndPassword(login, password);
	}


	}


