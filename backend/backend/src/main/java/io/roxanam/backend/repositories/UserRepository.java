package io.roxanam.backend.repositories;

import io.roxanam.backend.entities.User;
import io.roxanam.backend.entities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByUserType(UserType userType);
}
