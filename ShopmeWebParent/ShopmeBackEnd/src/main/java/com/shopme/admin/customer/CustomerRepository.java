package com.shopme.admin.customer;

import com.shopme.common.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {
    @Query("select c from Customer c where c.email = ?1")
    Customer findByEmail(String email);

    @Query("select c from Customer c where c.verificationCode = ?1")
    Customer findByVerificationCode(String code);

    @Query("update Customer c set c.enabled = ?2 where c.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    Long countById(Integer id);

    @Query("select c from Customer c where concat(c.firstName, ' ', c.lastName, ' ', c.email, ' ', c.addressLine1, ' ', c.addressLine2, ' ',c.city, ' ', c.state, ' ', c.country, ' ', c.postalCode) like %?1%")
    public Page<Customer> findAll(String keyword, Pageable pageable);
}
