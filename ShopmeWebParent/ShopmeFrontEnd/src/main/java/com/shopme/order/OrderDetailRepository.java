package com.shopme.order;

import com.shopme.common.entity.order.OrderDetail;
import com.shopme.common.entity.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query("select count(d) from OrderDetail d join OrderTrack t on d.order.id = t.order.id"
            + " where d.product.id = ?1 and d.order.customer.id = ?2 and t.status = ?3")
    public long countByProductAndCustomerAndOrderStatus(Integer productId, Integer customerId, OrderStatus status);
}
