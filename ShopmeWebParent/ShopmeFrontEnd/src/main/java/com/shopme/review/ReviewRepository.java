package com.shopme.review;

import com.shopme.common.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("select r from Review r where r.customer.id = ?1")
    Page<Review> findByCustomer(Integer customerId, Pageable pageable);

    @Query("select r from Review r where r.customer.id = ?1 and ("
            + "r.headline like %?2% or r.comment like %?2% or r.product.name like %?2%)")
    Page<Review> findByCustomer(Integer customerId, String keyword, Pageable pageable);

    @Query("select r from Review r where r.customer.id = ?1 and r.id = ?2")
    Review findByCustomerAndId(Integer customerId, Integer reviewId);
}
