package com.example.notifications.Services;

import com.example.notifications.Dto.InvestmentDto;

import java.util.List;
import java.util.Optional;

public interface InvestmentService {
    public List<InvestmentDto> getAllInvest();
    public Optional<InvestmentDto> findInvestById(Long userId , Long companyId);
    public InvestmentDto addInvest(InvestmentDto investmentDto);
    public InvestmentDto updateInvest(InvestmentDto investmentDto);
    public void DeleteById(Long userId ,Long companyId);
    public List<InvestmentDto> getInvestsByCompanyId(Long id);
}
