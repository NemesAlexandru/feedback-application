package org.fasttrackit.feedbackapplication.service;

import org.fasttrackit.feedbackapplication.domain.Review;
import org.fasttrackit.feedbackapplication.exception.ResourceNotFoundException;
import org.fasttrackit.feedbackapplication.persistance.ReviewRepository;
import org.fasttrackit.feedbackapplication.transfer.SaveReviewRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review createReview(SaveReviewRequest request){
        LOGGER.info("Creating review {}", request);
        Review review = new Review();
        review.setReviewName(request.getReviewName());
        review.setDescription(request.getDescription());
        return reviewRepository.save(review);
    }

    public Review getReview(long id) {

        LOGGER.info("Retrieving review {}", id);
        //using optional
        return reviewRepository.findById(id)
                //lambda expression
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review " + id + " does not exist."));
    }

    public Review updateReview(long id, SaveReviewRequest request) {
        LOGGER.info("Updating review {}: {}", id, request);

        Review review = getReview(id);

        BeanUtils.copyProperties(request, review);

        return reviewRepository.save(review);

    }

    public void deleteReview(long id) {
        LOGGER.info("Deleting review {}", id);
        reviewRepository.deleteById(id);
        LOGGER.info("Deleted review {}", id);

    }
}
