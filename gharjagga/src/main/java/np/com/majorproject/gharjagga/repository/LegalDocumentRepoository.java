package np.com.majorproject.gharjagga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import np.com.majorproject.gharjagga.entities.LegalDocument;
@Repository
public interface LegalDocumentRepoository extends JpaRepository<LegalDocument, Long> {

	
	@Modifying
	@Query(value="UPDATE tbl_legal_document SET availability="+false+" WHERE id=?1",nativeQuery = true)
	public void deleteById();
	
}
