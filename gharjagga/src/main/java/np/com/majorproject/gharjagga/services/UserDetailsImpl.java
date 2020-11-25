package np.com.majorproject.gharjagga.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import np.com.majorproject.gharjagga.entities.Users;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private String username;

	private String email;

	@JsonIgnore
	private String password;
	
	private String address;
	
	private Long contact;
	
	private String gender;
	
	private Boolean status;
	
	private Boolean emailStatus;

	
	private Collection<? extends GrantedAuthority> authorities;
	

	
	public UserDetailsImpl(Long id, String name, String username, String email, String password, String address,
			Long contact, String gender, Boolean status, Boolean emailStatus,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.address = address;
		this.contact = contact;
		this.gender = gender;
		this.status = status;
		this.emailStatus = emailStatus;
		this.authorities = authorities;
	}



	public static UserDetailsImpl build(Users user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(),
				user.getName(),
				user.getUsername(), 
				user.getEmail(),
				user.getPassword(),
				user.getAddress(),
				user.getContact(),
				user.getGender(),
				user.getEmailStatus(),
				user.getStatus(),
				authorities);
	}


	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	public String getEmail() {
		return email;
	}
	

	public String getAddress() {
		return address;
	}



	public Long getContact() {
		return contact;
	}



	public String getGender() {
		return gender;
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



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	
	
}
