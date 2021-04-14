package com.ecommercemetricsapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Metric {
	String type;
	Company company;
	float value;
	
}
