package com.ecommercemetricsapi.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercemetricsapi.models.Event;
import com.ecommercemetricsapi.repository.EventRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api/v1.0")
public class EventResource {

	@Autowired
	EventRepository eventRepository;
	
	@GetMapping(value="/event")
	@ApiOperation(value="Returns all events")
	public List<Event> getEvents(){
		return eventRepository.findAll();
	}
	
	@GetMapping(value="/event/{id}")
	@ApiOperation(value="Returns a given event")
	public Event getEvent(@PathVariable(value="id") long id){
		return eventRepository.findById(id);
	}
	
	@PostMapping("/event")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value="Add a new event")
	public Event createEvent(@RequestBody Event event) {
		return eventRepository.save(event);
	}
	
	@DeleteMapping("/event/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="Delete a given event")
	public void deleteEvent(@PathVariable(value="id") long id) {
		eventRepository.findById(id);
	}
}
