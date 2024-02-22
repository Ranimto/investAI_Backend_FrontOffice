package com.example.notifications.impl;

import com.example.notifications.Dto.InvestmentDto;
import com.example.notifications.Exception.ErrorCode;
import com.example.notifications.Repository.InvestmentRepo;
import com.example.notifications.Services.InvestmentService;
import com.example.notifications.models.InvestId;
import com.example.notifications.models.Investment;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.example.notifications.Exception.ErrorCode.INVESTMENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvestmentServiceImpl implements InvestmentService {
    private  final InvestmentRepo investmentRepo;
    private final ModelMapper modelMapper;

    public List<InvestmentDto> getAllInvest(){
        List<Investment> investments=investmentRepo.findAll();
        return investments.stream().map(u-> modelMapper.map(u, InvestmentDto.class)).collect(Collectors.toList());
    }

    public Optional<InvestmentDto> findInvestById(Long userId , Long companyId) {
        Optional<Investment> investment = investmentRepo.findByInvestId(userId, companyId);
        return Optional.ofNullable(investment.map(u -> modelMapper.map(u, InvestmentDto.class)).orElseThrow(() -> new EntityNotFoundException(INVESTMENT_NOT_FOUND.getMessage() )));
    }

    public InvestmentDto addInvest(InvestmentDto investmentDto){
        Investment investment = modelMapper.map(investmentDto, Investment.class);
        InvestId id = new InvestId(investmentDto.getUserId(), investmentDto.getCompanyId());
        investment.setId(id);
       Investment savedInvest = investmentRepo.save(investment);
        return modelMapper.map(savedInvest, InvestmentDto.class);
    }

    //*** A TESTER
    public InvestmentDto updateInvest(InvestmentDto investmentDto) {
        Long companyId = investmentDto.getCompanyId();
        Long userId = investmentDto.getUserId();

        Investment investment = investmentRepo.findByInvestId( userId,companyId).orElse(null);
        if (investment != null) {
            investment.setAmount(investmentDto.getAmount());
            investment.setDuration(investmentDto.getDuration());
            investment.setStartDate(investmentDto.getStartDate());
            investment.setStatus(investmentDto.getStatus());
            investment.setType(investmentDto.getType());
            Investment updatedInvest = investmentRepo.save(investment);
            return modelMapper.map(updatedInvest, InvestmentDto.class);
        } else {
            return null;
        }
    }

    public void DeleteById(Long userId ,Long companyId){
        investmentRepo.deleteInvestById(userId,companyId);
    }

    public List<InvestmentDto> getInvestsByInvestorId(Long id){
       List <Investment> investments = investmentRepo.findByUserId(id);
        return investments.stream().map(u-> modelMapper.map(u, InvestmentDto.class)).collect(Collectors.toList()) ;
    }

    public List<InvestmentDto> getInvestsByCompanyId(Long id){
        List <Investment> investments = investmentRepo.findByCompanyId(id);
        return investments.stream().map(u-> modelMapper.map(u, InvestmentDto.class)).collect(Collectors.toList()) ;
    }


}
