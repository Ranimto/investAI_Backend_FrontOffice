package com.example.notifications.Repository;

import com.example.notifications.models.ProfileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileDataRepo  extends JpaRepository<ProfileData,Long> {
    Optional<ProfileData> findByUserId(Long id);
}
