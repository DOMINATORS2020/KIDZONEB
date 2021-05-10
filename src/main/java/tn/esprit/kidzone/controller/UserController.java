package tn.esprit.kidzone.controller;


import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import tn.esprit.kidzone.entity.RoleName;
import tn.esprit.kidzone.entity.User;
import tn.esprit.kidzone.repository.UserRepository;
import tn.esprit.kidzone.services.EmailSenderService;
import tn.esprit.kidzone.services.IUserService;
import tn.esprit.kidzone.services.UserServiceImpl;


@Scope(value = "session")
@Component(value = "userController")
@ELBeanName(value = "userController")
@Join(path = "/", to = "/HomePage.jsf")
public class UserController {
	private  static String currentUser ;
	
	
	 @Autowired
	 EmailSenderService email;

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

	private String login; private String password; private User user; private String firstName;
	private String lastName; private boolean enabled;
    private Boolean loggedIn;
    @Autowired
    IUserService userService;
    @Autowired
    private UserServiceImpl userServiceImpl;
	@Autowired
	private UserRepository repo;
	private List<User> users;
	public UserController(){
		
		user = new User ();

		
		}

			public String doLogin() {
	        	String navigateTo = "null";
	        	User userAtt= repo.findEmail(login);
	        	User u=userService.authenticate(login, password);
	        	if(u != null &&  u.isEnabled() ){
	        	userServiceImpl.resetFailedAttempts(login);	
	        	currentUser = u.getLastName();
	        	if ( u.getRole() == RoleName.ADMINISTRATEUR &&  u.isEnabled()) {
	        	navigateTo = "/pages/admin/listusers2.xhtml?faces-redirect=true";
	        	this.name =u.getFirstName();
	        	loggedIn = true;
	        											
	        	}
	        	if(! u.isEnabled() ){
	        		loggedIn=false;
	        		FacesMessage facesMessage =
	    		        	new FacesMessage("Your account is locked right now ");

	    		        	FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);		
	        	}
	        	else if ( u.getRole() == RoleName.USER &&  u.isEnabled()) {
	        			navigateTo = "/pages/admin/listusers.xhtml?faces-redirect=true";
	        	loggedIn = true;
	        	
    		        
	        	}
	        	}
	        	else {
	        		
	        		if(repo.getfailedAttempt(login) < UserServiceImpl.MAX_FAILED_ATTEMPTS -1 ){
		        		userServiceImpl.increaseFailedAttempts(userAtt);
		        		FacesMessage facesMessage =
	        		        	new FacesMessage("Login failed ! Bad credentials");

	        		        	FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);
	        		        	repo.setEnabledtoFalse(login);
	        		        	
	        		}
	        		
	        		
	        		else{
	        			userServiceImpl.lock(userAtt);
	        			FacesMessage facesMessage =

	        		        	new FacesMessage("Your account has been locked due to 3 failed attempts."
                                    + " It will be unlocked after 24 hours");

	        		        	FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);
	        		}
	        		
	        	
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
				
				
			    public String getFirstName() {
					return firstName;
				}

				public void setFirstName(String firstName) {
					this.firstName = firstName;
				}

				public String getLastName() {
					return lastName;
				}

				public void setLastName(String lastName) {
					this.lastName = lastName;
				}

				public boolean isEnabled() {
					return enabled;
				}

				public void setEnabled(boolean enabled) {
					this.enabled = enabled;
				}

				public UserController(String login, String password, String firstName,
						String lastName, boolean enabled) {
		
					this.login = login;
					this.password = password;
					this.firstName = firstName;
					this.lastName = lastName;
					this.enabled = enabled;
				}

				public UserController(String login, String password, String firstName, String lastName) {
					super();
					this.login = login;
					this.password = password;
					this.firstName = firstName;
					this.lastName = lastName;
				}

				public String deleteUser(Long id){
					userService.deleteUser(id);
					return "/listusers2.xhtml?faces-redirect=true";
				}
				
			   
			    public List<User> getUsers() {
			    	return userService.retrieveAllUsers();
				}

				public void setUsers(List<User> users) {
					this.users = users;
				}

				public String AjouterUser(){
			    userService.addUser(new User(login,firstName,password,lastName));
			    SimpleMailMessage mailMessage = new SimpleMailMessage();
		        mailMessage.setTo(login);
		        mailMessage.setSubject("Complete Registration!");
		        mailMessage.setFrom("mouradjomaa9@gmail.com");
		        mailMessage.setText("Welcome to our plateform , Your account has been activated now  "
		                );

		        email.sendEmail(mailMessage);
					return "/HomePage.xhtml?faces-redirect=true";
			    	
			    }
	    }




