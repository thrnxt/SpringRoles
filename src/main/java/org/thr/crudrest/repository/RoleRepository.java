package org.thr.crudrest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.thr.crudrest.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
