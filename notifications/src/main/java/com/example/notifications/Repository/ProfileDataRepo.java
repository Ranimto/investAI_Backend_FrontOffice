package com.example.notifications.Repository;

import com.example.notifications.models.ProfileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileDataRepo  extends JpaRepository<ProfileData,Long> {
}
