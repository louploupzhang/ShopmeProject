package com.shopme.review;

import com.shopme.common.entity.Review;
import com.shopme.common.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("select r from Review r where r.customer.id = ?1")
    Page<Review> findByCustomer(Integer customerId, Pageable pageable);

    @Query("select r from Review r where r.customer.id = ?1 and ("
            + "r.headline like %?2% or r.comment like %?2% or r.product.name like %?2%)")
    Page<Review> findByCustomer(Integer customerId, String keyword, Pageable pageable);

    @Query("select r from Review r where r.customer.id = ?1 and r.id = ?2")
    Review findByCustomerAndId(Integer customerId, Integer reviewId);

    Page<Review> findByProduct(Product product, Pageable pageable);

    @Query("select count(r.id) from Review r where r.customer.id = ?1 and r.product.id = ?2")
    Long countByCustomerAndProduct(Integer customerId, Integer productId);

    @Query("update Review r set r.votes = coalesce((select sum(v.votes) from ReviewVote v where v.review.id = ?1), 0)" +
            " where r.id = ?1")
    @Modifying
    void updateVoteCount(Integer reviewId);

    @Query("select r.votes from Review r where r.id = ?1")
    Integer getVoteCount(Integer reviewId);
}
