package np.com.majorproject.gharjagga.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.util.StringUtils;



import net.bytebuddy.utility.RandomString;
import np.com.majorproject.gharjagga.entities.PropertyDetails;

import np.com.majorproject.gharjagga.repository.PropertyDetailsRepository;
import np.com.majorproject.gharjagga.repository.PropertyPictureRepository;
import np.com.majorproject.gharjagga.services.PropertyPictureService;
import np.com.majorproject.gharjagga.services.UserDetailsImpl;
import np.com.majorproject.gharjagga.utils.AppConstants;

@RestController
@RequestMapping("api/propertypicture")
public class PropertyPictureController {

	@Autowired
	PropertyPictureRepository pPictureRepo;
	
	@Autowired
	private PropertyPictureService fileStorageService;
	
	@Autowired
	private PropertyDetailsRepository propertyDetailsrepo;


	@PostMapping("/save/{id}")

	public ResponseEntity<String> savePropertyPictures(@RequestParam MultipartFile file, @PathVariable Long id){

		//to get current logged in user

		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		String username=((UserDetailsImpl)principal).getUsername();
		
		Optional<PropertyDetails>pro=propertyDetailsrepo.findById(id);
		PropertyDetails p=pro.get();
		
		String propertyOwner=p.getPropertyOwner();

		//to get current date and time 
		DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		LocalDateTime currentTime= LocalDateTime.now();
		
		//To generate random alphanumeric characters.
			//String randomChars = RandomString.makecters.
			String randomChars = RandomString.make();

			String fileName=StringUtils.cleanPath(file.getOriginalFilename());
			String newFileName=randomChars+"_"+username+"_"+fileName;

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH_FOR_PROFILE)
					.path(newFileName).toUriString();
		
			fileStorageService.storeFile(file,newFileName,fileDownloadUri,username,p,propertyOwner);
		
		//PropertyPictures addPicture= return null; 
	//return ResponseEntity.ok("Property Picture uploaded successfully.");
			return ResponseEntity.ok("Property Picture uploaded successfully");
		}
	
	@PutMapping("/deletePicture/{id}")
	
	public ResponseEntity<?> deletePicture( @PathVariable Long id){
		
		pPictureRepo.deleteById(id);
		return ResponseEntity.ok("picture deleted successfully");
	}
		


}
