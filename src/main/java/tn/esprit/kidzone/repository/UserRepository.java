package tn.esprit.kidzone.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.kidzone.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	public User getUserByLoginAndPassword(String login, String password);
	
}
