package tn.esprit.kidzone.services;
import java.util.List;

import tn.esprit.kidzone.entity.*;


public interface IUserService {
	List<User> retrieveAllUsers();
	User addUser(User u);
	void deleteUser(String id);
	User updateUser(User u);
	User retrieveUser(String id);
	
	public User authenticate(String login, String password);
	

}
