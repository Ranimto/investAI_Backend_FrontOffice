package com.example.notifications.Services;

import com.example.notifications.Dto.CompanyDto;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    CompanyDto addCompany(CompanyDto companyDto);
    Optional<CompanyDto> findCompanyById(Long id);
    List<CompanyDto> getAllCompany();
    CompanyDto UpdateCompany(CompanyDto companyDto);
    void deleteCompany(Long id);
    List<CompanyDto> getCompaniesByInvestor(Long investorId);
    CompanyDto getCompanyByRIB(double RIB);
    double getCompanyBalanceByRIB(double RIB);
}
