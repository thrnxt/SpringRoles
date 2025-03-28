package org.thr.crudrest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.thr.crudrest.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
