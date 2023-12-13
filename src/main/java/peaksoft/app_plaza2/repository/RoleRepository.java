package peaksoft.app_plaza2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.app_plaza2.model.entties.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role ,Long> {
    @Query("select role from Role role where role.name=:roleName")
   Role findByName(@Param("roleName")String roleName);
}
