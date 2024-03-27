package com.example.notifications.Controllers;

import com.example.notifications.Dto.InvestmentDto;
import com.example.notifications.impl.InvestmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/investment")
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentServiceImpl investmentService;

    @GetMapping("/getAll")
    public ResponseEntity<List<InvestmentDto>> getAllInvestments() {
        List<InvestmentDto> investments = investmentService.getAllInvest();
        return new ResponseEntity<>(investments, HttpStatus.OK);
    }

    @GetMapping("/{investorId}/{companyId}")
    public ResponseEntity<InvestmentDto> getInvestmentById(@PathVariable Long investorId, @PathVariable Long companyId) {
        Optional<InvestmentDto> investmentDto = investmentService.findInvestById(investorId,companyId);
        if (investmentDto.isPresent()) {
            return new ResponseEntity<>(investmentDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<InvestmentDto> addInvestment( @RequestBody InvestmentDto investmentDto) {
        InvestmentDto savedInvestment = investmentService.addInvest(investmentDto);
        return new ResponseEntity<>(savedInvestment, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<InvestmentDto> updateInvestment( @RequestBody InvestmentDto investmentDto) {
        InvestmentDto updatedInvestment = investmentService.updateInvest(investmentDto);
        return new ResponseEntity<>(updatedInvestment, HttpStatus.OK);
    }

    @DeleteMapping("delete/{investorId}/{companyId}")
    public ResponseEntity<Void> deleteInvestmentById(@PathVariable Long investorId, @PathVariable Long companyId ) {
        if (!investmentService.findInvestById(investorId,companyId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            investmentService.DeleteById( investorId ,companyId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("getInvest/{investorId}")
    public ResponseEntity<List<InvestmentDto>> getInvestmentsByInvestorId(@PathVariable Long investorId) {
        List<InvestmentDto> investments = investmentService.getInvestsByInvestorId(investorId);
        return ResponseEntity.ok(investments);
    }

    @GetMapping("getCompany/{companyId}")
    public ResponseEntity<List<InvestmentDto>> getInvestmentsByCompanyId(@PathVariable Long companyId) {
        List<InvestmentDto> investments = investmentService.getInvestsByCompanyId(companyId);
        return ResponseEntity.ok(investments);
    }
}
