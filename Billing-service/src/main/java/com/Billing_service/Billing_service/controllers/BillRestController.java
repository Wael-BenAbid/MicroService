package com.Billing_service.Billing_service.controllers;

import com.Billing_service.Billing_service.entities.Bill;
import com.Billing_service.Billing_service.proxy.CustomerServiceClient;
import com.Billing_service.Billing_service.proxy.InventoryServiceClient;
import com.Billing_service.Billing_service.repos.BillRepository;
import com.Billing_service.Billing_service.repos.ProductItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BillRestController {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private CustomerServiceClient customerServiceClient;
    @Autowired
    private InventoryServiceClient inventoryServiceClient;

    @GetMapping("/bills/full/{id}")
    Bill getBill(@PathVariable(name="id") Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found with id: " + id));
        bill.setCustomer(customerServiceClient.findCustomerById(bill.getCustomerID()));
        bill.setProductItems(productItemRepository.findByBillId(id));
        bill.getProductItems().forEach(pi -> {
            pi.setProduct(inventoryServiceClient.findProductById(pi.getProductID()));
        });
        return bill;
    }

}
