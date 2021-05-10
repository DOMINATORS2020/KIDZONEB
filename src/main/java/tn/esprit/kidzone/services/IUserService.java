package tn.esprit.kidzone.services;
import java.util.List;

import tn.esprit.kidzone.entity.*;


public interface IUserService {
	List<User> retrieveAllUsers();
	void addUser(User u);
	void deleteUser(Long id);
	User updateUser(User u);
	User retrieveUser(String id);

	
	public User authenticate(String login, String password);
	

}
