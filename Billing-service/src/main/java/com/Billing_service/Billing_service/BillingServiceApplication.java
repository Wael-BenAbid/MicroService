package com.Billing_service.Billing_service;

import com.Billing_service.Billing_service.entities.Bill;
import com.Billing_service.Billing_service.entities.Customer;
import com.Billing_service.Billing_service.entities.productItem;
import com.Billing_service.Billing_service.proxy.CustomerServiceClient;
import com.Billing_service.Billing_service.proxy.InventoryServiceClient;
import com.Billing_service.Billing_service.repos.BillRepository;
import com.Billing_service.Billing_service.repos.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);



	}

	@Bean
	CommandLineRunner start(BillRepository billRepository,
							ProductItemRepository productItemRepository,
							InventoryServiceClient inventoryServiceClient,
							CustomerServiceClient customerServiceClient) {
		return args -> {
			Bill bill = new Bill();
			bill.setBillingDate(new Date());

			// Recherche d'un client avec l'ID 1
			Customer customer = customerServiceClient.findCustomerById(1L);
			if (customer != null) {
				bill.setCustomer(customer);
				billRepository.save(bill);


				inventoryServiceClient.findAll().getContent().forEach(p -> {
					productItem productItem = new productItem();
					productItem.setBill(bill);
					productItem.setProduct(p);
					productItem.setPrice(p.getPrice());
					productItem.setQuantity((int) (1 + Math.random() * 1000));
					productItemRepository.save(productItem);
				});
			} else {
				System.out.println("Aucun client trouvé avec l'ID 1");
			}
		};
	}




}
