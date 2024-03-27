package com.example.notifications.Repository;

import com.example.notifications.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CompanyRepo extends JpaRepository<Company,Long> {

    @Transactional
    @Query("SELECT c FROM Company c WHERE c.id IN (SELECT i.company.id FROM Investment i WHERE i.user.id = :idInvestor)")
    List<Company> getCompaniesByInvestorId(Long idInvestor);

    @Transactional
    @Query("SELECT c FROM Company c WHERE c.id IN (SELECT i.company.id FROM Investment i WHERE i.user.id <> :idInvestor)")
    List<Company> getAllCompaniesExceptByInvestorId(Long idInvestor);
    Company getCompanyByRIB( double RIB);

}
