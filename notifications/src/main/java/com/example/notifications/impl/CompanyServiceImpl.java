package com.example.notifications.impl;

import com.example.notifications.Dto.CompanyDto;
import com.example.notifications.Repository.CompanyRepo;
import com.example.notifications.Services.CompanyService;
import com.example.notifications.models.Company;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.notifications.Exception.ErrorCode.COMPANY_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepo companyRepo;
    private final ModelMapper modelMapper;

    public CompanyDto addCompany(CompanyDto companyDto) {
        Company company = modelMapper.map(companyDto, Company.class);
        Company savedCompany = companyRepo.save(company);
        return modelMapper.map(savedCompany, CompanyDto.class);
    }

    public Optional<CompanyDto> findCompanyById(Long id) {
        if (id == 0) {
            log.error("Id is null");
        }
        Optional<Company> company = companyRepo.findById(id);
        return Optional.ofNullable(company.map(u -> modelMapper.map(u, CompanyDto.class)).orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND.getMessage())));
    }

    public List<CompanyDto> getAllCompany() {
        List<Company> companies = companyRepo.findAll();
        return companies.stream().map(u -> modelMapper.map(u, CompanyDto.class)).collect(Collectors.toList());
    }


    public CompanyDto UpdateCompany(CompanyDto companyDto) {
        Company company = companyRepo.findById(companyDto.getId()).orElse(null);
        if (company != null) {
            company.setName(companyDto.getName());
            company.setAddress(companyDto.getAddress());
            company.setActivity(companyDto.getActivity());
            company.setDebtRatio(companyDto.getDebtRatio());
            company.setDescription(companyDto.getDescription());
            company.setInterestCoverageRatio(companyDto.getInterestCoverageRatio());
            company.setLiquidityRatio(companyDto.getLiquidityRatio());
            company.setNbOfInvestors(companyDto.getNbOfInvestors());
            company.setProfitabilityRatio(companyDto.getProfitabilityRatio());
            company.setSalesGrowthRatio(companyDto.getSalesGrowthRatio());
            company.setRIB(companyDto.getRIB());

            Company updatedCompany = companyRepo.save(company);
            return modelMapper.map(updatedCompany, CompanyDto.class);
        } else {
            return null;
        }
    }

    public void deleteCompany(Long id) {
        companyRepo.deleteById(id);
    }
}
