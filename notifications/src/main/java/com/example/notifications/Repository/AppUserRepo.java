package com.example.notifications.Repository;

import com.example.notifications.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser,Long> {
    AppUser findByFirstname(String name);
    Optional<AppUser> findByEmail(String email);

    // Méthode automatiquement générée par Spring Data JPA
    Integer countByEmail(String email);


    @Transactional
    @Query("SELECT u FROM AppUser u WHERE u.verificationCode = ?1")
     AppUser findByVerificationCode(String code);


}
