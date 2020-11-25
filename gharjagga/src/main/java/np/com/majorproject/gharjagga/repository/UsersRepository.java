package np.com.majorproject.gharjagga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import np.com.majorproject.gharjagga.entities.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	
	@Modifying
	@Query(value="UPDATE users SET status="+false+" WHERE id=?1",nativeQuery = true)
	public void deleteById();
}
