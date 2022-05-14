package com.mg.homework.accountservices;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mg.homework.accountservices.service.CustomerService;

@SpringBootApplication
public class AccountservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountservicesApplication.class, args);
	}

	@Bean
    public CommandLineRunner loadData(CustomerService service) {
        return args -> {
        	service.populateCustomerData();
        };
	}
}
