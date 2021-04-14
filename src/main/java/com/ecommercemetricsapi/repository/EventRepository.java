package com.ecommercemetricsapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommercemetricsapi.models.Company;
import com.ecommercemetricsapi.models.Event;

public interface EventRepository extends JpaRepository<Event,Long>{
	Event findById(long id);
	List<Event> findByType(String type);
	List<Event> findByCompany(Company company);
}
