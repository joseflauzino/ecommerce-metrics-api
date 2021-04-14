package com.ecommercemetricsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommercemetricsapi.models.Company;

public interface CompanyRepository extends JpaRepository<Company,Long>{
	Company findById(long id);
}
