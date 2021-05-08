package tn.esprit.kidzone.controller;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tn.esprit.kidzone.entity.RoleName;
import tn.esprit.kidzone.entity.User;
import tn.esprit.kidzone.services.IUserService;
@ManagedBean
@ViewScoped
@Scope(value = "session")
@Component(value = "userController")
@ELBeanName(value = "userController")
@Join(path = "/", to = "/HomePage.jsf")
public class UserController {
	private  static String currentUser ;
	
	
	 

	public static String getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(String currentUser) {
		UserController.currentUser = currentUser;
	}


	private String name ;
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String login; private String password; private User user;
    private Boolean loggedIn;
    @Autowired
    IUserService userService;
	    
	    


			public String doLogin() {
	        	String navigateTo = "null";
	        	
	        	User u=userService.authenticate(login, password);
	        	currentUser = u.getLastName();
	        	if (u != null && u.getRole() == RoleName.ADMINISTRATEUR) {
	        	navigateTo = "/pages/admin/listusers.xhtml?faces-redirect=true";
	        	this.name =u.getFirstName();
	        	loggedIn = true;
	        	
	        	}
	        	else if (u != null && u.getRole() == RoleName.USER) {
	        			navigateTo = "/welcome.xhtml?faces-redirect=true";
	        	loggedIn = true;
	        	
	        	
	        	}
	        	else {
	        	FacesMessage facesMessage =

	        	new FacesMessage("Login Failed: please check your username/password and try again.");

	        	FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);
	        	}
	        	return navigateTo;
	        	}

	        	public String doLogout() {
	        	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	        	return "/SpringMVC/SignIN.jsf?faces-redirect=true";
	        	}

				public String getLogin() {
					return login;
				}

				public void setLogin(String login) {
					this.login = login;
				}

				public String getPassword() {
					return password;
				}

				public void setPassword(String password) {
					this.password = password;
				}

				public User getUser() {
					return user;
				}

				public void setUser(User user) {
					this.user = user;
				}

				public Boolean getLoggedIn() {
					return loggedIn;
				}

				public void setLoggedIn(Boolean loggedIn) {
					this.loggedIn = loggedIn;
				}
				
			    public List<User> getUsers(){
			    	
			    	
			    	return userService.retrieveAllUsers();
			    }
			    
			    public void addUser(User u){
			    	
			    	u.setEnabled(true);
			    	u.setRole(RoleName.USER);
			    	
			    	userService.addUser(u);
			    	
			    }
	    }




