package com.shopme.admin.order;

import com.shopme.common.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {
    @Query("select o from Order o where concat('#', o.id) like %?1% or"
            + " concat(o.firstName, ' ', o.lastName) like %?1% or"
            + " o.firstName like %?1% or"
            + " o.lastName like %?1% or o.phoneNumber like %?1% or"
            + " o.addressLine1 like %?1% or o.addressLine2 like %?1% or"
            + " o.postalCode like %?1% or o.city like %?1% or"
            + " o.state like %?1% or o.country like %?1% or"
            + " o.paymentMethod like %?1% or o.status like %?1% or"
            + " o.customer.firstName like %?1% or o.customer.lastName like %?1%")
    Page<Order> findAll(String keyword, Pageable pageable);

    Long countById(Integer id);

    @Query("select new com.shopme.common.entity.order.Order(o.id, o.orderTime, o.productCost, o.subtotal, o.total)"
            + " from Order o where o.orderTime between ?1 and ?2 order by o.orderTime asc")
    List<Order> findByOrderTimeBetween(Date startTime, Date endTime);
}
