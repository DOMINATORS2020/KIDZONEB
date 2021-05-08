package tn.esprit.kidzone.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "users")
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true, length = 45)
	private String login;
	@Column (nullable = false, length = 20)
	private String firstName;
	@Column (nullable = false, length = 64)
	private String password;
	@Column (nullable = false, length = 20)
	private String lastName;
	@Column (nullable = false)
	private boolean enabled;
	@Column (nullable = false)
	@Enumerated (EnumType.STRING) 
	private RoleName role;
	@Temporal (TemporalType.DATE)
	private Date date;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public RoleName getRole() {
		return role;
	}
	public void setRole(RoleName role) {
		this.role = role;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public User(Long id, String login, String firstName, String password, String lastName, boolean enabled, RoleName role,
			Date date) {
		super();
		this.id = id;
		this.login = login;
		this.firstName = firstName;
		this.password = password;
		this.lastName = lastName;
		this.enabled = enabled;
		this.role = role;
		this.date = date;
	}
	
	public User() {
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + login + ", firstName=" + firstName + ", password=" + password
				+ ", lastName=" + lastName + ", enabled=" + enabled + ", role=" + role + ", date=" + date + "]";
	}
	public User (String name){
		this.firstName=name;
	}

}