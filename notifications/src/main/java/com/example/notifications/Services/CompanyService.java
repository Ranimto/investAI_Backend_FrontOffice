package com.example.notifications.Services;

import com.example.notifications.Dto.CompanyDto;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    public CompanyDto addCompany(CompanyDto companyDto);
    public Optional<CompanyDto> findCompanyById(Long id);
    public List<CompanyDto> getAllCompany();
    public CompanyDto UpdateCompany(CompanyDto companyDto);
    public void deleteCompany(Long id);
}
