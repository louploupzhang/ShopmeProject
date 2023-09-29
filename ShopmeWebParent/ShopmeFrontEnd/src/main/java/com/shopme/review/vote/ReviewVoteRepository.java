package com.shopme.review.vote;

import com.shopme.common.entity.ReviewVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewVoteRepository extends JpaRepository<ReviewVote, Integer> {

    @Query("select v from ReviewVote v where v.review.id = ?1 and v.customer.id = ?2")
    ReviewVote findByReviewAndCustomer(Integer reviewId, Integer customerId);

    @Query("select v from ReviewVote v where v.review.product.id = ?1 and v.customer.id = ?2")
    List<ReviewVote> findByProductAndCustomer(Integer productId, Integer customerId);
}
