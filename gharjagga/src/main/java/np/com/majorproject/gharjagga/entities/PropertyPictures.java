package np.com.majorproject.gharjagga.entities;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_property_pictures")
public class PropertyPictures {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String propertyOwner;
	
	private String uploadedBy;
	
	private String uploadedTime;
	
	private String uploadDir;
	
	private String fileType;
	

	@ManyToOne(fetch=FetchType.EAGER , cascade=CascadeType.ALL)
	private PropertyDetails propertyDetails;
	
	

	public PropertyPictures() {
		super();
		
	}



	public PropertyPictures(String propertyOwner, String uploadedBy, String uploadedTime, String uploadDir,
			String fileType, List<PropertyDetails> propertyDetails) {
		super();
	
		this.propertyOwner = propertyOwner;
		this.uploadedBy = uploadedBy;
		this.uploadedTime = uploadedTime;
		this.uploadDir = uploadDir;
		this.fileType = fileType;
		//this.propertyDetails = propertyDetails;
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



	public PropertyDetails getPropertyDetails() {
		return propertyDetails;
	}



	public void setPropertyDetails(PropertyDetails propertyDetails) {
		this.propertyDetails = propertyDetails;
	}





}
