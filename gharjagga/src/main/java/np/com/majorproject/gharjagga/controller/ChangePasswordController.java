package np.com.majorproject.gharjagga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import np.com.majorproject.gharjagga.exception.ResourceNotFoundException;
import np.com.majorproject.gharjagga.repository.UsersRepository;
import np.com.majorproject.gharjagga.request.ChangePasswordRequest;
import np.com.majorproject.gharjagga.services.UserDetailsImpl;



@RestController
@RequestMapping("/api/changepassword")
public class ChangePasswordController {

	@Autowired
	UsersRepository userRepo;

	@Autowired
	PasswordEncoder encoder;


	@PutMapping("/resetpassword/{username}")
	public ResponseEntity<?>fogretPass(@PathVariable String username,@RequestBody ChangePasswordRequest changePassword){
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String loggedUsername=((UserDetailsImpl)principal).getUsername();
		if(loggedUsername.equals(username)) {

			String newPassword=changePassword.getNewPassword();
			String confirmPassword=changePassword.getConfirmPassword();
			try {
				if(newPassword.equals(confirmPassword)) {

					userRepo.findByUsername(username).map(changePass->{
						changePass.setPassword(encoder.encode(newPassword));
						return userRepo.save(changePass);



					}).orElseThrow(() -> new ResourceNotFoundException("Username " + username + " not found"));
					return ResponseEntity.ok("Password Changed Successfully");
				}
				else {
					return ResponseEntity.ok("Cannot change!!! please confirm your password");
				}
			}catch(Exception e) {
				return ResponseEntity.ok(e.getMessage());
			}
		}
		else {
			return ResponseEntity.ok("You can't change other's password");
		}


	}

}
