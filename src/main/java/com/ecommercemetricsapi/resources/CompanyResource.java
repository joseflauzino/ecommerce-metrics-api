package com.ecommercemetricsapi.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercemetricsapi.models.Company;
import com.ecommercemetricsapi.repository.CompanyRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api/v1.0")
@Api(value="Company REST API")
@CrossOrigin(origins="*")
public class CompanyResource {

	@Autowired
	CompanyRepository companyRepository;
	
	@GetMapping(value="/company")
	@ApiOperation(value="Returns all companies")
	public List<Company> getCompanies(){
		return companyRepository.findAll();
	}
	
	@GetMapping(value="/company/{id}")
	@ApiOperation(value="Returns a given company")
	public Company getCompany(@PathVariable(value="id") long id){
		return companyRepository.findById(id);
	}

	@PostMapping("/company")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value="Create a company")
	public Company createCompany(@RequestBody Company company) {
		return companyRepository.save(company);
	}
	
	@DeleteMapping("/company/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="Delete a company")
	public void deleteCompany(@PathVariable(value="id") long id) {
		companyRepository.findById(id);
	}
}
