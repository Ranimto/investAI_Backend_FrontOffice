package com.example.notifications.Repository;

import com.example.notifications.models.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestmentRepo extends JpaRepository<Investment,Long> {


    List<Investment> findByUserId(Long id);

    List<Investment> findByCompanyId(Long id);

    @Transactional
    @Query("SELECT i FROM Investment i WHERE i.user.id = :userId AND i.company.id = :companyId")
    Optional<Investment> findByInvestId(Long userId, Long companyId);

    @Transactional
    @Modifying
    @Query("delete FROM Investment i WHERE i.user.id = :userId  AND i.company.id = :companyId")
    void deleteInvestById(Long userId, Long companyId);
}
