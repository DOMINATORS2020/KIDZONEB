package tn.esprit.kidzone.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.kidzone.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	public User getUserByLoginAndPassword(String login, String password);
	public User save(User u);
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.failedAttempt=:failedAttempt WHERE u.login=:login")
    void updateFailedAttempts( @Param("failedAttempt") int failAttempts, @Param("login") String login);
	
	@Query(value = "SELECT u.failedAttempt FROM User u where u.login =:login  ")
	int  getfailedAttempt(@Param("login") String login);
	
	@Query("select u from User u where u.login=:login")
	User findEmail(@Param("login") String login);
	
	@Modifying
	@Transactional
	@Query("Update User u set u.enabled= false where u.login=:login")
	void setEnabledtoFalse(@Param("login") String login);
}
