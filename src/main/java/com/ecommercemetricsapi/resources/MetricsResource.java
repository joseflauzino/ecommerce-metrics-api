package com.ecommercemetricsapi.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercemetricsapi.models.Company;
import com.ecommercemetricsapi.models.Event;
import com.ecommercemetricsapi.models.Metric;
import com.ecommercemetricsapi.repository.EventRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api/v1.0")
public class MetricsResource {

	@Autowired
	EventRepository eventRepository;
	
	@GetMapping(value="/metric")
	@ApiOperation(value="Returns all metrics of a given company")
	public List<Metric> getmetrics(@RequestBody Company company){
		
		return null;
	}
	
	@GetMapping(value="/metric/conversion-rate")
	@ApiOperation(value="Returns the conversion rate of a given company")
	public Metric getConversionRate(@RequestBody Company company){
		// (sales number ÷ visit number) x 100
		List<Event> sales = new ArrayList<>();
		for (Event e : eventRepository.findAll()) {
			System.out.println(e);
			if(e.getCompany().equals(company) && "sale".equals(e.getType())) {
				sales.add(e);
			}
		}
		List<Event> visits = new ArrayList<>();
		for (Event e : eventRepository.findAll()) {
			if(e.getCompany().equals(company) && "visit".equals(e.getType())) {
				visits.add(e);
			}
		}
		float conversionRate = ((float)sales.size() / (float)visits.size()) * 100;
		return new Metric("conversion-rate", company, conversionRate);
	}
	
	@GetMapping(value="/metric/cart-abandonment-rate")
	@ApiOperation(value="Returns the cart abandonment rate of a given company")
	public Metric getCartAbandonmentRate(@RequestBody Company company){
		// (number of carts with added products ÷ sales number ) x 100
		List<Event> carts = new ArrayList<>();
		for (Event e : eventRepository.findAll()) {
			if(e.getCompany().equals(company) && "cart".equals(e.getType())) {
				carts.add(e);
			}
		}
		List<Event> sales = new ArrayList<>();
		for (Event e : eventRepository.findAll()) {
			if(e.getCompany().equals(company) && "sale".equals(e.getType())) {
				sales.add(e);
			}
		}
		float cartAbandonmentRate = (sales.size() / carts.size()) * 100;
		return new Metric("cart-abandonment-rate", company, cartAbandonmentRate);
	}
	
	@GetMapping(value="/metric/approved-order-rate")
	@ApiOperation(value="Returns the approved order rate of a given company")
	public Metric getApprovedOrderRate(@RequestBody Company company){
		// (number of paid orders ÷ number of orders) x 100
		List<Event> paidOrders = new ArrayList<>();
		for (Event e : eventRepository.findAll()) {
			if(e.getCompany().equals(company) && "paid-order".equals(e.getType())) {
				paidOrders.add(e);
			}
		}
		List<Event> orders = new ArrayList<>();
		for (Event e : eventRepository.findAll()) {
			if(e.getCompany().equals(company) && "order".equals(e.getType())) {
				orders.add(e);
			}
		}
		float approvedOrderRate = (paidOrders.size() / orders.size()) * 100;
		return new Metric("approved-order-rate", company, approvedOrderRate);
	}
	
	@DeleteMapping("/metric/{id}")
	@ApiOperation(value="Delete given metric")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEvent(@PathVariable(value="id") long id) {
		eventRepository.findById(id);
	}
}
