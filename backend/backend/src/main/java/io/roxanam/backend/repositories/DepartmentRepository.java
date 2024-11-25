package io.roxanam.backend.repositories;

import io.roxanam.backend.models.LoungeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoungeTypeRepository extends JpaRepository<LoungeType, Long> {
    Optional<LoungeType> findByName(String name);
    void deleteByName(String name);


}
