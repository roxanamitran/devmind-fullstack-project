package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.User;
import io.roxanam.backend.entities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByIdAndIsActiveTrue(Long id);
    List<User> findAllByUserTypeAndIsActiveTrue(UserType userType);
    Optional<User> findByEmailAndIsActiveTrue(String email);
    @Query(value = "SELECT u.* FROM user u " +
            "LEFT JOIN salon_employees se ON u.id = se.employees_id " +
            "WHERE se.salon_id IS NULL AND u.user_type = 'EMPLOYEE'", nativeQuery = true)
    List<User> findAllAvailableEmployees();
}
