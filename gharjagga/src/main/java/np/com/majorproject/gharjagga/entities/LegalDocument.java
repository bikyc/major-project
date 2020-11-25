package np.com.majorproject.gharjagga.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_legal_document")
public class LegalDocument {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String propertyOwner;
	
	private String uploadedBy;
	
	private String uploadedTime;
	
	private String uploadDir;
	
	private String fileType;
	
	private String fileName;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	private PropertyDetails propertyDetails;
	/*
	 * @ManyToOne(fetch=FetchType.EAGER) private PropertyDetails propertyDetails;
	 */

	public LegalDocument() {
		super();
	
	}

	public LegalDocument(String propertyOwner, String uploadedBy, String uploadedTime, String uploadDir,
			String fileType, String fileName, List<PropertyDetails> propertyDetails) {
		super();
		this.propertyOwner = propertyOwner;
		this.uploadedBy = uploadedBy;
		this.uploadedTime = uploadedTime;
		this.uploadDir = uploadDir;
		this.fileType = fileType;
		this.fileName = fileName;
		//this.propDetails = propertyDetails;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPropertyOwner() {
		return propertyOwner;
	}

	public void setPropertyOwner(String propertyOwner) {
		this.propertyOwner = propertyOwner;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public String getUploadedTime() {
		return uploadedTime;
	}

	public void setUploadedTime(String uploadedTime) {
		this.uploadedTime = uploadedTime;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setPropertyDetails(PropertyDetails p) {
		this.propertyDetails= p;
		
	}

	public PropertyDetails getPropertyDetails() {
		return propertyDetails;
	}
	

	
 
	
	

}
