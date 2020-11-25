package np.com.majorproject.gharjagga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import np.com.majorproject.gharjagga.entities.ERole;
import np.com.majorproject.gharjagga.entities.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
	Optional<Roles> findByName(ERole name);
}
