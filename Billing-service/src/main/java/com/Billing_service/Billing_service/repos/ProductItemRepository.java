package com.Billing_service.Billing_service.repos;

import com.Billing_service.Billing_service.entities.productItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<productItem, Long> {
    List<productItem> findByBillId(Long billID);
}
