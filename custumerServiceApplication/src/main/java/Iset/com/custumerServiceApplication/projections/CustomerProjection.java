package Iset.com.custumerServiceApplication.projections;

import Iset.com.custumerServiceApplication.models.Customer;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "fullCustomer", types = Customer.class)
public interface CustomerProjection {
    Long getId();
    String getName();
    String getEmail();
}