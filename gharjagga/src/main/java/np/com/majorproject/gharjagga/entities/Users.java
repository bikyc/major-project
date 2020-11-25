package np.com.majorproject.gharjagga.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;

@Entity
public class Users {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max=50)
	private String name;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String email;
	
	@NotBlank
	@Size(min=6,max=30)
	private String password;
	
	private String address;
	
	@NotBlank
	private Long contact;
	
	@NotBlank
	private String gender;
	
	private Boolean status=true;
	
	private Boolean emailStatus=false;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns=@JoinColumn(name="user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))

	private Set<Roles> roles = new HashSet<>();


	

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Users(@NotBlank @Size(max = 50) String name, @NotBlank String username, @NotBlank String email,
			@NotBlank @Size(min = 6, max = 30) String password, String address, @NotBlank Long contact,
			@NotBlank String gender, Set<Roles> roles) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.address = address;
		this.contact = contact;
		this.gender = gender;
		this.roles = roles;
		/*
		 * this.emailStatus=false; this.status=true;
		 */
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Long getContact() {
		return contact;
	}


	public void setContact(Long contact) {
		this.contact = contact;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public Set<Roles> getRoles() {
		return roles;
	}


	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public Boolean getEmailStatus() {
		return emailStatus;
	}


	public void setEmailStatus(Boolean emailStatus) {
		this.emailStatus = emailStatus;
	}


	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", password="
				+ password + ", address=" + address + ", contact=" + contact + ", gender=" + gender + ", status="
				+ status + ", emailStatus=" + emailStatus + ", roles=" + roles + "]";
	}
	


	


	
	
	
	
	}
