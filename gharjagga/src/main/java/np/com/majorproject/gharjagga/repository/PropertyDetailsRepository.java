package np.com.majorproject.gharjagga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import np.com.majorproject.gharjagga.entities.PropertyDetails;


@Repository
public interface PropertyDetailsRepository extends JpaRepository<PropertyDetails, Long> {
	
	@Query(value="select * from property_details where city like %:city% and status='1'" , nativeQuery = true)
	List<PropertyDetails> findByCity(String city); 
	
	@Query(value="select * from property_details where type like %:type% and status='1'",nativeQuery = true)
	List<PropertyDetails> findByType(String type); 
	
	@Query(value="select * from property_details where purpose like %:purpose% and status='1'",nativeQuery = true)
	List<PropertyDetails> findByPurpose(String purpose); 
	
	@Modifying
	@Query(value="UPDATE property_details set status="+false+" WHERE id=?1",nativeQuery = true)
	public void deleteById(Long id);

	@Query(value="SELECT * FROM property_details ORDER BY (price) ASC", nativeQuery=true)
	List<PropertyDetails> getPropertyByPriceInAscOrder();

	@Query(value="SELECT * FROM property_details ORDER BY (price) DESC", nativeQuery=true)
	List<PropertyDetails> getPropertyByPriceInDescOrder();
	
	
	@Query(value="SELECT * FROM property_details WHERE price BETWEEN ?1 AND ?2 ORDER BY (price) ASC",nativeQuery = true)
	List<PropertyDetails> getPropertyByRangeOfPrice(Long from, Long to);
	
}
