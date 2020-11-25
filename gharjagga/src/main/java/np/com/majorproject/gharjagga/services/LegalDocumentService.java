package np.com.majorproject.gharjagga.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import np.com.majorproject.gharjagga.entities.LegalDocument;
import np.com.majorproject.gharjagga.entities.PropertyDetails;
import np.com.majorproject.gharjagga.entities.PropertyPictures;
import np.com.majorproject.gharjagga.exception.FileStorageException;
import np.com.majorproject.gharjagga.repository.LegalDocumentRepoository;
import np.com.majorproject.gharjagga.repository.PropertyPictureRepository;
import np.com.majorproject.gharjagga.utils.AppConstants;

@Service
public class LegalDocumentService {

	@Value("${file.storage.location}")
	public String uploadDir;


	@Autowired
	LegalDocumentRepoository legalDocumentRepository;

	private Path fileStoragePath;
	public String fileStorageLocation;

	public LegalDocumentService(@Value("${file.storage.location:temp}") String fileStorageLocation) {

		this.fileStorageLocation = fileStorageLocation;
		fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();

		try {
			Files.createDirectories(fileStoragePath);
		} catch (IOException e) {
			throw new RuntimeException("Issue in creating file directory");
		}
	}


	public LegalDocument storeFile(MultipartFile file,String newFileName,String fileDownloadUri,String username,PropertyDetails p,String propertyOwner) {

		if(!(file.getOriginalFilename().endsWith(AppConstants.PNG_FILE_FORMAT) || file.getOriginalFilename().endsWith(AppConstants.JPEG_FILE_FORMAT)||file.getOriginalFilename().endsWith(AppConstants.JPG_FILE_FORMAT))) 
			throw new FileStorageException(AppConstants.INVALID_FILE_FORMAT);
		// Normalize file name


		Path filePath = Paths.get(fileStoragePath + "\\" + newFileName);

		try {
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Issue in storing the file", e);
		}


		try {
			// Check if the file's name contains invalid characters
			if (newFileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + newFileName);
			}

			//getting local date and time
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now(); 


			LegalDocument addDoc=new LegalDocument();

			addDoc.setFileName(newFileName);
			addDoc.setFileType(file.getContentType());
			addDoc.setUploadDir(fileDownloadUri);
			addDoc.setUploadedBy(username);
			addDoc.setUploadedTime(dtf.format(now));
			addDoc.setPropertyOwner(propertyOwner);
			addDoc.setPropertyDetails(p);

			return legalDocumentRepository.save(addDoc);
		} catch (Exception ex) {
			throw new FileStorageException("Could not store file " + newFileName + ". Please try again!", ex);
		}
	}
}


