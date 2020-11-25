package np.com.majorproject.gharjagga.entities;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PropertyDetails {
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private Long id;
	 
	 @NotBlank
	 private String type;
	 
	 @NotBlank
	 private Long price;
	 
	 @NotBlank
	 private String location;
	 
	 @NotBlank
	 private String city;
	 
	 @NotBlank
	 private String purpose;
	 
	 @NotBlank
	 private boolean availability ;
	 
	 @NotBlank
	 private String description;
	 
	 @NotBlank
	 private boolean verification;
	 
	 @NotBlank
	 private String propertyOwner;
	 
	 @NotBlank
	 private String propertyAddedTime;
	 
	 @NotBlank
	 private String ownerContact;
	 
	 @NotBlank
	 private boolean status;

	 @JsonIgnore
	 @OneToMany(mappedBy = "propertyDetails", cascade=CascadeType.ALL)
	 private List<PropertyPictures> propertyPic=new ArrayList<>();
	
	
	 @JsonIgnore
	 @OneToMany(mappedBy ="propertyDetails" )
	 	private List<LegalDocument> legalDoc=new ArrayList<>();
	
	 

	public PropertyDetails() {
	}




	public PropertyDetails(String type, Long price, String location, String city, String purpose, 
			String description,  String propertyOwner, String propertyAddedTime,
			String ownerContact) {
		super();
		this.type = type;
		this.price = price;
		this.location = location;
		this.city = city;
		this.purpose = purpose;
		this.availability = true;
		this.description = description;
		this.verification = false;
		this.propertyOwner = propertyOwner;
		this.propertyAddedTime = propertyAddedTime;
		this.ownerContact = ownerContact;
		this.status=true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isVerification() {
		return verification;
	}

	public void setVerification(boolean verification) {
		this.verification = verification;
	}

	public String getPropertyOwner() {
		return propertyOwner;
	}

	public void setPropertyOwner(String propertyOwner) {
		this.propertyOwner = propertyOwner;
	}

	public String getPropertyAddedTime() {
		return propertyAddedTime;
	}

	public void setPropertyAddedTime(String propertyAddedTime) {
		this.propertyAddedTime = propertyAddedTime;
	}

	public String getOwnerContact() {
		return ownerContact;
	}

	public void setOwnerContact(String ownerContact) {
		this.ownerContact = ownerContact;
	}

	public List<PropertyPictures> getPropertyPic() {
		return propertyPic;
	}

	public void setPropertyPic(List<PropertyPictures> propertyPic) {
		this.propertyPic = propertyPic;
	}

	public List<LegalDocument> getLegalDoc() {
		return legalDoc;
	}

	public void setLegalDoc(List<LegalDocument> legalDoc) {
		this.legalDoc = legalDoc;
	}

	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}
	
	


	
	
}
	 
	
	 
	 

	