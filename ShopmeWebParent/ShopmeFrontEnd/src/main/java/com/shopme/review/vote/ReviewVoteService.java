package com.shopme.review.vote;

import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Review;
import com.shopme.common.entity.ReviewVote;
import com.shopme.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ReviewVoteService {
    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private ReviewVoteRepository voteRepo;

    public VoteResult undoVote(ReviewVote vote, Integer reviewId, VoteType voteType) {
        voteRepo.delete(vote);
        reviewRepo.updateVoteCount(reviewId);
        Integer voteCount = reviewRepo.getVoteCount(reviewId);

        return VoteResult.success("You have successfully unvoted " + voteType + " that review.", voteCount);
    }

    public VoteResult doVote(Integer reviewId, Customer customer, VoteType voteType) {
        Review review = null;
        try {
            review = reviewRepo.findById(reviewId).get();
        } catch (NoSuchElementException e) {
            return VoteResult.fail("The review ID " + reviewId + " no longer exists.");
        }

        ReviewVote vote = voteRepo.findByReviewAndCustomer(reviewId, customer.getId());

        if (vote != null) {
            if (vote.isUpvoted() && voteType.equals(VoteType.UP) || vote.isDownvoted() && voteType.equals(VoteType.DOWN)) {
                return undoVote(vote, reviewId, voteType);
            } else if (vote.isUpvoted() && voteType.equals(VoteType.DOWN)) {
                vote.voteDown();
            } else if (vote.isDownvoted() && voteType.equals(VoteType.UP)) {
                vote.voteUp();
            }
        } else {
            vote = new ReviewVote();
            vote.setCustomer(customer);
            vote.setReview(review);

            if (voteType.equals(VoteType.UP)) {
                vote.voteUp();
            } else {
                vote.voteDown();
            }
        }

        voteRepo.save(vote);
        reviewRepo.updateVoteCount(reviewId);
        Integer voteCount = reviewRepo.getVoteCount(reviewId);

        return VoteResult.success("You have successfully voted " + voteType + " that review.", voteCount);
    }
}
