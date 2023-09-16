package com.shopme.order;

import com.shopme.common.entity.Customer;
import com.shopme.common.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.customer.id = ?1")
    Page<Order> findAll(Integer customerId, Pageable pageable);

    @Query("select distinct o from Order o join o.orderDetails od join od.product p where o.customer.id = ?2 "
            + "and (p.name like %?1% or o.status like %?1%)")
    Page<Order> findAll(String keyword, Integer customerId, Pageable pageable);

    Order findByIdAndCustomer(Integer id, Customer customer);
}
