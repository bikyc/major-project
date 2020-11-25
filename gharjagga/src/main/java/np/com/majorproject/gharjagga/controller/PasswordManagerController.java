package np.com.majorproject.gharjagga.controller;

import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import np.com.majorproject.gharjagga.entities.Users;
import np.com.majorproject.gharjagga.exception.ResourceNotFoundException;
import np.com.majorproject.gharjagga.repository.UsersRepository;
import np.com.majorproject.gharjagga.request.ChangePasswordRequest;
import np.com.majorproject.gharjagga.services.EmailService;
import np.com.majorproject.gharjagga.services.OTPService;

import np.com.majorproject.gharjagga.utils.AppConstants;
	
	
@RestController
@RequestMapping("/api/forgetpassword")
public class PasswordManagerController {


	@Autowired
	EmailService emailService;

	@Autowired
	OTPService otpService;

	@Autowired
	UsersRepository userRepo;
	@Autowired
	PasswordEncoder encoder;


	@PutMapping("/changepassword/{username}")
	public ResponseEntity<?>forgotPassword(@PathVariable String username,@RequestBody ChangePasswordRequest changePassword){

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
	// change your password at your will


	//recover password by sending email
	@PostMapping("/recover/{username}")
	public ResponseEntity<?> forgetPassword(@PathVariable String username, @RequestBody Users user){
		Optional<Users> userDetails= userRepo.findByUsername(username);
		Users u =userDetails.get();
		Long contact= u.getContact();
		String email=u.getEmail();
		//manual input from user
		Long contactInput=user.getContact();
		String emailInput=user.getEmail();
		if(contactInput.equals(contact) &&  emailInput.equals(email))
		{
			int otp=otpService.generateOTP(username);
			String msg="Hello!! "+username+"This is your OTP: "+ String.valueOf(otp);
			try {
				emailService.sendOtpMessage(email, "OTP For Password Recovery",msg);

			} catch (MessagingException e) {

				e.printStackTrace();
			}
			return ResponseEntity.ok("OTP has been send to email Please check your mail inbox");
		}

		else {

			return ResponseEntity.ok("This Email and Contact are not associated to your id");
		}

	}
	@PostMapping("/validateOtp/{username}")
	public String validateOtp(@RequestParam("OTPnum") int OTPnum,@PathVariable String username){

		final String fail = "Entered Otp is NOT valid. Please Retry!";
		//Validate the Otp
		if(OTPnum >= 0){

			//int serverOtp = otpService.getOtp(username);
			int serverOtp=otpService.getOtp(username);
			if(serverOtp > 0){
				if(OTPnum == serverOtp){
					otpService.clearOTP(username);

					String clickme = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.PATH_FOR_PASSWORD_CHANGE)
							.path(username).toUriString();

					return (clickme);
				}
				else {
					return fail;
				}
			}else {
				return "Server otp not found";
			}
		}else {
			return fail;
		}

	}
	
}