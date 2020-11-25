package np.com.majorproject.gharjagga.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import np.com.majorproject.gharjagga.entities.PropertyDetails;
import np.com.majorproject.gharjagga.repository.PropertyDetailsRepository;
import np.com.majorproject.gharjagga.services.UserDetailsImpl;

@RestController
@RequestMapping("api/property")
public class PropertyDetailController {
	
	
	@Autowired 
	PropertyDetailsRepository pDetailsRepo;
	
	
	
	@PostMapping("/save")
	@PreAuthorize(" hasRole('USER') or  hasRole('ADMIN')")
	
	public ResponseEntity<?> savePropertyDeatil(@RequestBody PropertyDetails pDetails){
	
		//to get current logged in user
		
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=((UserDetailsImpl)principal).getUsername();
		
		//to get current date and time
		DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime currentTime= LocalDateTime.now();
		
		PropertyDetails addProperty= new PropertyDetails(pDetails.getType(),
														pDetails.getPrice(),
														pDetails.getLocation(),
														pDetails.getCity(),
														pDetails.getPurpose(),
														pDetails.getDescription(),
														username,
														dtf.format(currentTime),
														pDetails.getOwnerContact()
														
															);
															
		addProperty.setPropertyPic(pDetails.getPropertyPic());
		//addProperty.setLegalDoc(pDetails.getLegalDoc());
															 
		
		
		pDetailsRepo.save(addProperty);
		
		return ResponseEntity.ok("Property added successfully, please wait for verification");
	}
	
	@GetMapping("/listOfProperty")
	@PreAuthorize( "hasRole('ADMIN') or hasRole('USER') or userRole('MODERATOR')")
	
	public List<PropertyDetails> listOfProperty(){
		return pDetailsRepo.findAll();
		
	}
	
	@GetMapping("/search/ById/{id}")
	@PreAuthorize( "hasRole('ADMIN') or hasRole('USER') or userRole('MODERATOR')")
	
	public Optional<PropertyDetails> listOfPropertyById(@PathVariable Long id){
		return pDetailsRepo.findById(id);		
		
	}
	
	@GetMapping("/search/ByType/{type}")
	@PreAuthorize( "hasRole('ADMIN') or hasRole('USER') or userRole('MODERATOR')")
	
	public List<PropertyDetails> listOfPropertyByType(@PathVariable String type){
		return pDetailsRepo.findByType(type);	
		
	}
	
	@GetMapping("/search/ByCity/{city}")
	@PreAuthorize( "hasRole('ADMIN') or hasRole('USER') or userRole('MODERATOR')")
	
	public List<PropertyDetails> listOfPropertyByCity(@PathVariable String city){
		return pDetailsRepo.findByCity(city);		
		
	}
		
	@GetMapping("/search/ByPurpose/{purpose}")
	@PreAuthorize( "hasRole('ADMIN') or hasRole('USER') or userRole('MODERATOR')")
		
	public List<PropertyDetails> listOfPropertyByPurpose(@PathVariable String purpose){
		return pDetailsRepo.findByPurpose(purpose);		
			
		}
	
	@GetMapping("/sort/ByPriceInAsc")
	public List<PropertyDetails> listOfPropertyByPriceInAsc(){
		return pDetailsRepo.getPropertyByPriceInAscOrder();
	}
	

	@GetMapping("/sort/ByPriceInDesc")
	public List<PropertyDetails> listOfPropertyByPriceInDesc(){
		return pDetailsRepo.getPropertyByPriceInDescOrder();
	}
	
	@GetMapping("/search/propertyByPriceRange/{from}/{to}")
	public List<PropertyDetails>propertyByPriceRange(@PathVariable Long from,@PathVariable Long to){
		
		return pDetailsRepo.getPropertyByRangeOfPrice(from,to);
	}
	
	
	@PutMapping("/updateProperty/{id}")
	@PreAuthorize( "hasRole('ADMIN') or hasRole('USER') or userRole('MODERATOR')")
	
	public PropertyDetails updateProperty (@RequestBody PropertyDetails pDetails, @PathVariable Long id){
		
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=((UserDetailsImpl)principal).getUsername();
		
		Optional<PropertyDetails> a=pDetailsRepo.findById(id);
		
		PropertyDetails b=a.get();
		String propertyOwner=b.getPropertyOwner();
		if(username.equals(propertyOwner)) {
			
		
		try {
			
			return  pDetailsRepo.findById(id).map(updateProperty->{
					
				updateProperty.setAvailability(pDetails.isAvailability());
//				updateProperty.setCity(pDetails.getCity());
				updateProperty.setDescription(pDetails.getDescription());
				
			//	updateProperty.setLocation(pDetails.getLocation());
				updateProperty.setOwnerContact(pDetails.getOwnerContact());
				updateProperty.setPrice(pDetails.getPrice());
				updateProperty.setPurpose(pDetails.getPurpose());
				updateProperty.setType(pDetails.getType());
			
				
			  return pDetailsRepo.save(updateProperty);}).orElseThrow(()->new Exception("id"+id+"not found"));
	
		} 
		catch (Exception e) {
			return null;
		
		}
		}
		else {
			return null;
		}
		
		}
		
	@PutMapping("/deleteProperty/{id}")
	@PreAuthorize( "hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> deleteProperty( @PathVariable Long id){
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=((UserDetailsImpl)principal).getUsername();
		Optional<PropertyDetails> propertydetail=pDetailsRepo.findById(id);
		PropertyDetails pdtl=propertydetail.get();
		String propertyOwner=pdtl.getPropertyOwner(); 
		
		if(username.equals(propertyOwner)) {
		pDetailsRepo.deleteById(id);
		return ResponseEntity.ok("deleted successfully");
	}
		else {
			return ResponseEntity.ok("you cannot delete others property");
			
		}
		
	}
}
