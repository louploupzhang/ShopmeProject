package com.shopme.admin.order;

import com.shopme.common.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {
    @Query("select o from Order o where o.firstName like %?1% or"
            + " o.lastName like %?1% or o.phoneNumber like %?1% or"
            + " o.addressLine1 like %?1% or o.addressLine2 like %?1% or"
            + " o.postalCode like %?1% or o.city like %?1% or"
            + " o.state like %?1% or o.country like %?1% or"
            + " o.paymentMethod like %?1% or o.status like %?1% or"
            + " o.customer.firstName like %?1% or o.customer.lastName like %?1%")
    Page<Order> findAll(String keyword, Pageable pageable);
}
