package np.com.majorproject.gharjagga.response;

import java.util.List;

public class JwtResponse {
	private String token;
//	private String type = "Bearer";
	private Long id;
	private String name;
	private String username;
	private String email;
	private Long contact;
	
	
	private String gender;
	private String address;
	
	private List<String> roles;

	public JwtResponse(String token, Long id, String name, String username, String email, Long contact, String gender,
			String address, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.contact = contact;
		this.gender = gender;
		this.address = address;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}
