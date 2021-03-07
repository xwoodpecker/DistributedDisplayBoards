package htw.vs.data;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Role repository.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find by name role.
     *
     * @param roleName the role name
     * @return the role
     */
    Role findByName(String roleName);
}
