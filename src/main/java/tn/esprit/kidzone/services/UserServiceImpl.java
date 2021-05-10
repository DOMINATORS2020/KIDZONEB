package tn.esprit.kidzone.services;

import java.util.Date;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import tn.esprit.kidzone.entity.RoleName;
import tn.esprit.kidzone.entity.User;
import tn.esprit.kidzone.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService{
	private static final Logger l = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository repo;
	@Autowired
	UserServiceImpl userService;
	@Autowired
	

	@Override
	public List<User> retrieveAllUsers() {
		List<User> list = (List<User>) repo.findAll();
		for (User user : list) {
			 l.info("" + user);
			 
		}
		return list;
	}

	@Override
	public void addUser(User user) {
		user.setEnabled(true);
		user.setRole(RoleName.USER);
	    repo.save(user);
	    
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
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

	/* First, we declare the maximum number of failed login attempts allowed */
	public static final int MAX_FAILED_ATTEMPTS = 3;
	/* Second we declare duration of the lock time in milliseconds */
	private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24hours

	/*
	 * this method updates the number of failed attempts of a user. It is called
	 * each time the user fails to login (CallBack Exception Bad credentials or
	 * another exception generated has injected in method Authenticate).
	 */
	public void increaseFailedAttempts(User userAttempt) {
		int newFailAttempts = userAttempt.getFailedAttempt() + 1;
		repo.updateFailedAttempts(newFailAttempts, userAttempt.getLogin());
		l.info("Failed attempts has been affected by 1 **successefully**");
	}
	/* sets the number of failed attempts to zero. This method will be called when the user has logged in successfully. */
	public void resetFailedAttempts(String email) {
		repo.updateFailedAttempts(0, email);
		l.info("Failed attempts has been all reseted **successefully**");
	}

	/*  locks the user’s account if the number of failed logins reach the maximum allowed times */
	public void lock(User userAttempt) {
		userAttempt.setAccountNonLocked(false);
		userAttempt.setLockTime(new Date());

		repo.save(userAttempt);
		l.info("Account of "+userAttempt.getFirstName()+" "+userAttempt.getLastName()+" has been locked **successefully** ");
	}

	/*  unlocks the user’s account when lock duration expires, allowing the user to login as usual */
	public boolean unlockWhenTimeExpired(User userToUnlock) {
		long lockTimeInMillis = userToUnlock.getLockTime().getTime();
		long currentTimeInMillis = System.currentTimeMillis(); // SYSTEM.DATETIME NOW

		if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
			userToUnlock.setAccountNonLocked(true);
			userToUnlock.setLockTime(null);
			userToUnlock.setFailedAttempt(0);

			repo.save(userToUnlock);
			l.info("Account of "+userToUnlock.getFirstName()+" "+userToUnlock.getLastName()+" has been unlocked **successefully**");
			return true;
		}

		return false;
	}





	

	}


