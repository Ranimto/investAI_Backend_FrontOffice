package com.example.notifications.Controllers;

import com.example.notifications.Dto.CompanyDto;

import com.example.notifications.impl.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyServiceImpl companyService;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findCompanyById(@PathVariable("id") Long id) {
        Optional<CompanyDto> companyDto = companyService.findCompanyById(id);
        if (companyDto.isPresent()) {
            return new ResponseEntity<>(companyDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> companies = companyService.getAllCompany();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CompanyDto> addCompany(@RequestBody CompanyDto companyDto) {
        CompanyDto addedCompany = companyService.addCompany(companyDto);
        return new ResponseEntity<>(addedCompany, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CompanyDto> updateCompany(@RequestBody CompanyDto companyDto) {
        CompanyDto updatedCompany = companyService.UpdateCompany(companyDto);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteCompany(@PathVariable("id") Long id) {
        if (!companyService.findCompanyById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            companyService.deleteCompany(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
