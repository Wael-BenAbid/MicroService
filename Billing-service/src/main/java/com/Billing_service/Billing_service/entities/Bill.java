package com.Billing_service.Billing_service.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date billingDate;
    private long customerID;

    @OneToMany(mappedBy = "bill")
    private Collection<productItem> productItems;

    @Transient
    private Customer customer;




}
