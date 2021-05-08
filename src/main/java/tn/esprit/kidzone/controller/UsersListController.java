package tn.esprit.kidzone.controller;

import java.util.List;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tn.esprit.kidzone.entity.User;
import tn.esprit.kidzone.repository.UserRepository;
import tn.esprit.kidzone.services.UserServiceImpl;

@Scope(value = "session")
@Component(value = "usersList")
@ELBeanName(value = "UsersList")
@Join(path = "/admin/", to = "/listusers.jsf")
public class UsersListController {

	 @Autowired
	    private UserRepository userRepository;
	 @Autowired
        private UserServiceImpl userService;
	    private List<User> users;

	    @Deferred
	    @RequestAction
	    @IgnorePostback
	    public void loadData() {
	        users = (List<User>) userRepository.findAll();
	    }

	    public List<User> getProducts() {
	        return users;
	    }
	    
	    public List<User> getUsers(){
	    	
	    	
	    	return userService.retrieveAllUsers();
	    }
	   
	}
