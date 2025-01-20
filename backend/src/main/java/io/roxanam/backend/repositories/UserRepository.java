package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.User;
import io.roxanam.backend.entities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByIdAndIsActiveTrue(Long id);
    List<User> findAllByUserTypeAndIsActiveTrue(UserType userType);

}
