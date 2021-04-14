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
import com.ecommercemetricsapi.repository.CompanyRepository;
import com.ecommercemetricsapi.repository.EventRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api/v1.0")
public class MetricsResource {

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	@GetMapping(value="/company/{id}/metric")
	@ApiOperation(value="Returns all metrics of a given company")
	public List<Metric> getmetrics(@PathVariable(value="id") long id){
		List<Metric> metrics = new ArrayList<>();
		metrics.add(getConversionRate(id));
		metrics.add(getCartAbandonmentRate(id));
		metrics.add(getApprovedOrderRate(id));
		return metrics;
	}
	
	@GetMapping(value="/company/{id}/metric/conversion-rate")
	@ApiOperation(value="Returns the conversion rate of a given company")
	public Metric getConversionRate(@PathVariable(value="id") long id){
		// (sales number ÷ visit number) x 100
		Company company = companyRepository.findById(id);
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
	
	@GetMapping(value="/company/{id}/metric/cart-abandonment-rate")
	@ApiOperation(value="Returns the cart abandonment rate of a given company")
	public Metric getCartAbandonmentRate(@PathVariable(value="id") long id){
		// (number of carts with added products ÷ sales number ) x 100
		Company company = companyRepository.findById(id);
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
		float cartAbandonmentRate = ((float)sales.size() / (float)carts.size()) * 100;
		return new Metric("cart-abandonment-rate", company, cartAbandonmentRate);
	}
	
	@GetMapping(value="/company/{id}/metric/approved-order-rate")
	@ApiOperation(value="Returns the approved order rate of a given company")
	public Metric getApprovedOrderRate(@PathVariable(value="id") long id){
		// (number of paid orders ÷ number of orders) x 100
		// sale == paid-order
		Company company = companyRepository.findById(id);
		List<Event> paidOrders = new ArrayList<>();
		for (Event e : eventRepository.findAll()) {
			if(e.getCompany().equals(company) && "sale".equals(e.getType())) {
				paidOrders.add(e);
			}
		}
		List<Event> orders = new ArrayList<>();
		for (Event e : eventRepository.findAll()) {
			if(e.getCompany().equals(company) && "order".equals(e.getType())) {
				orders.add(e);
			}
		}
		float approvedOrderRate = ((float)paidOrders.size() / (float)orders.size()) * 100;
		return new Metric("approved-order-rate", company, approvedOrderRate);
	}
}
