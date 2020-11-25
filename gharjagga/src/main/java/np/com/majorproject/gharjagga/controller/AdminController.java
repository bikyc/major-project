package np.com.majorproject.gharjagga.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import np.com.majorproject.gharjagga.entities.ERole;
import np.com.majorproject.gharjagga.entities.Roles;
import np.com.majorproject.gharjagga.repository.PropertyDetailsRepository;
import np.com.majorproject.gharjagga.repository.RoleRepository;
import np.com.majorproject.gharjagga.repository.UsersRepository;
import np.com.majorproject.gharjagga.services.UserDetailsImpl;

@RestController

@RequestMapping("/api/admin")

public class AdminController {

	@Autowired
	UsersRepository userRepository;


	@Autowired
	PropertyDetailsRepository pDetailsRepo;
	
	@Autowired
	RoleRepository roleRepository;

	@PreAuthorize("hasRole ('ADMIN')")
	@PutMapping("/deleteuser/{id}")
	
	public ResponseEntity<?> deleteuser( @PathVariable Long id){

		userRepository.deleteById(id);
		return ResponseEntity.ok("user deleted successfully");
	}

	@PreAuthorize("hasRole ('ADMIN')")
	@PutMapping("/deleteProperty/{id}")
	public ResponseEntity<?> deleteProperty( @PathVariable Long id){
		/*
		 * Object
		 * principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal
		 * (); Long userid=((UserDetailsImpl)principal).getId();
		 */


		pDetailsRepo.deleteById(id);
		return ResponseEntity.ok("deleted successfully");
	}

	@PreAuthorize("hasRole ('ADMIN') or hasRole('MODREATOR')")
	@PutMapping("/verifyProperty/{id}")
	public ResponseEntity<?>verifyProperty(@PathVariable Long id){


		pDetailsRepo.findById(id).map(update->{
			update.setVerification(true);
			return pDetailsRepo.save(update);

		});


		return ResponseEntity.ok("Property Verified");
	}
	
	@PreAuthorize("hasRole ('ADMIN')")
	@PutMapping("/setModerator/{id}")
	public ResponseEntity<?> giveRole(@PathVariable Long id){

		Set<Roles>roles=new HashSet<>();
		
		Roles userRole=roleRepository.findByName(ERole.ROLE_MODERATOR)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		
		userRepository.findById(id).map(update->{
			update.setRoles(roles);
			return userRepository.save(update);

		});

		return ResponseEntity.ok("Role changed to moderator");
	}
	
	@PreAuthorize("hasRole ('ADMIN')")
	@PutMapping("/setUser/{id}")
	public ResponseEntity<?> setUser(@PathVariable Long id){

		Set<Roles>roles=new HashSet<>();
		
		Roles userRole=roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		
		userRepository.findById(id).map(update->{
			update.setRoles(roles);
			return userRepository.save(update);

		});

		return ResponseEntity.ok("Role changed to user");
	}
}



