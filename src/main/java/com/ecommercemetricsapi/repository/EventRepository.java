package com.ecommercemetricsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommercemetricsapi.models.Event;

public interface EventRepository extends JpaRepository<Event,Long>{
	Event findById(long id);
}
