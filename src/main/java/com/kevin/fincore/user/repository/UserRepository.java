package com.kevin.fincore.user.repository;

import com.kevin.fincore.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> { // Interface que estende JpaRepository para fornecer operações CRUD para a entidade User
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
