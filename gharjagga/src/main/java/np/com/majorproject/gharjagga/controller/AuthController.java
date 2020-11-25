package np.com.majorproject.gharjagga.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.PathVariable;

import np.com.majorproject.gharjagga.entities.ERole;
import np.com.majorproject.gharjagga.entities.Roles;
import np.com.majorproject.gharjagga.entities.Users;
import np.com.majorproject.gharjagga.jwt.JwtUtils;
import np.com.majorproject.gharjagga.repository.RoleRepository;
import np.com.majorproject.gharjagga.repository.UsersRepository;
import np.com.majorproject.gharjagga.request.LoginRequest;
import np.com.majorproject.gharjagga.request.SignUpRequest;
import np.com.majorproject.gharjagga.response.JwtResponse;
import np.com.majorproject.gharjagga.services.EmailService;
import np.com.majorproject.gharjagga.services.OTPService;
import np.com.majorproject.gharjagga.services.UserDetailsImpl;
import np.com.majorproject.gharjagga.utils.AppConstants;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsersRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	@Autowired

	EmailService emailService;

	@Autowired
	OTPService otpService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

				Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String loggedUser=((UserDetailsImpl)principal).getUsername();

				Optional<Users>userD=userRepository.findByUsername(loggedUser);
				Users u=userD.get();

				

		Boolean emailStatus=u.getEmailStatus();
		Boolean status=u.getStatus();
		
		String email=u.getEmail();
		
	if(status==true){

		if(emailStatus==true) {
			return ResponseEntity.ok(
					new JwtResponse(jwt,userDetails.getId(),
							userDetails.getName(),
							userDetails.getUsername(),
							userDetails.getEmail(),
							userDetails.getContact(),												
							userDetails.getGender(),
							userDetails.getAddress(),
							roles)
					);

		}else {
			int otp = otpService.generateOTP(loggedUser);
			String clickme = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.PATH_TO_VERIFY_EMAIL)
					.path(loggedUser).toUriString();
			String msg="This is your OTP: "+ String.valueOf(otp);
			String finalmsg=" Please check your email inbox and verify it before login! And follow this link to valdate: "+clickme;

			try {
				emailService.sendOtpMessage(email, "OTP For Email Verification",msg);
			} catch (javax.mail.MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return ResponseEntity.ok(finalmsg);
		}
	}else{
		return ResponseEntity.ok("Please check your credentials.");
	}
	}

	@PostMapping("/verifyemail/{username}")
	public ResponseEntity<?> verifyEmail(@RequestParam("OTPnum") int OTPnum,@PathVariable String username)
	{



		if(OTPnum >= 0){

			int serverOtp=otpService.getOtp(username);
			if(serverOtp > 0){
				if(OTPnum == serverOtp){
					otpService.clearOTP(username);

					userRepository.findByUsername(username).map(enableUser->{
						enableUser.setEmailStatus(true);
						return userRepository.save(enableUser);
					});
					//users.setStatus(true);
					return ResponseEntity.ok("Congrulation Email has been verified please login to proceed");
				}
				else {
					return ResponseEntity.ok( "server otp and the otp you sent did not match");
				}
			}else {
				return ResponseEntity.ok("Server otp not found");
			}
		}else {
			return ResponseEntity.ok("Otp from you is not received");
		}}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Email is already in use!");
		}

		// Create new user's account
		Users user = new Users();
		user.setName(signUpRequest.getName());
		user.setUsername(signUpRequest.getUsername());
		user.setEmail(signUpRequest.getEmail());

		user.setPassword(encoder.encode(signUpRequest.getPassword()));

		user.setAddress(signUpRequest.getAddress());
		user.setContact(signUpRequest.getContact());

		user.setGender(signUpRequest.getGender());



		Set<String> strRoles = signUpRequest.getRole();
		Set<Roles> roles = new HashSet<>();

		if (strRoles == null) {
			Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Roles adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Roles modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		String username=signUpRequest.getUsername();
		String email=signUpRequest.getEmail();
		int otp=otpService.generateOTP(username);
		String clickme = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.PATH_TO_VERIFY_EMAIL)
				.path(username).toUriString();
		String msg="This is your OTP: "+ String.valueOf(otp)+ "   Please follow this link  and enter OTP number to verify it.    "    +clickme;
		String finalmsg="User registered successfully!! please check your email inbox and verify it before login! And follow this link to valdate: "+clickme;
		try {
			emailService.sendOtpMessage(email, "OTP For Email Verification",msg);

		} catch (MessagingException e) {

			e.printStackTrace();
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(finalmsg);




	}

	@PutMapping("updateUserInfo/{id}")
	public Users updateUserInfo(@PathVariable Long id ,@RequestBody Users user) {


		Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId=((UserDetailsImpl)principal).getId();
		try{
			if(id==userId){
				return userRepository.findById(userId).map(updateUser->{
					updateUser.setEmail(user.getEmail());
					updateUser.setAddress(user.getAddress());
					updateUser.setContact(user.getContact());
					updateUser.setGender(user.getGender());
					updateUser.setName(user.getName());
					return userRepository.save(updateUser);
				}).orElseThrow(()->new Exception("id"+id+"not found"));

			}
		}catch(Exception e){
			return null;
		}
		return null;
	}

}






