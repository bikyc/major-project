package np.com.majorproject.gharjagga.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {
	@NotBlank
	@Size(max = 50)
	private String name;
	
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
	
	@NotBlank
	@Size(max = 10, min = 10)
	private Long contact;
	

	
	private Boolean status;
	private Boolean emailStatus;
	@Size(max = 20)
	private String gender;
	
	@NotBlank
	@Size(max = 200)
	private String address;
	

    
    private Set<String> role;



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



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public Set<String> getRole() {
		return role;
	}



	public void setRole(Set<String> role) {
		this.role = role;
	}
    
    
}
