package com.shopme.review;

import com.shopme.common.entity.Review;
import com.shopme.common.entity.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository repo;

    @Test
    public void testFindByCustomerNoKeyword() {
        Integer customerId = 5;
        Pageable pageable = PageRequest.of(1, 5);

        Page<Review> page = repo.findByCustomer(customerId, pageable);
        long totalElements = page.getTotalElements();

        assertThat(totalElements).isGreaterThan(0);
    }

    @Test
    public void testFindByCustomerWithKeyword() {
        Integer customerId = 5;
        String keyword = "Canon";
        Pageable pageable = PageRequest.of(1, 5);

        Page<Review> page = repo.findByCustomer(customerId, pageable);
        long totalElements = page.getTotalElements();

        assertThat(totalElements).isGreaterThan(0);
    }

    @Test
    public void testFindByCustomerAndId() {
        Integer customerId = 5;
        Integer reviewId = 1;
        Review review = repo.findByCustomerAndId(customerId, reviewId);
        assertThat(review).isNotNull();
    }

    @Test
    public void testFindByProduct(){
        Product product = new Product(2);
        Pageable pageable=PageRequest.of(0,3);
        Page<Review> page = repo.findByProduct(product, pageable);

        assertThat(page.getTotalElements()).isGreaterThan(1);

        List<Review> content = page.getContent();
        content.forEach(System.out::println);
    }

    @Test
    public void testCountyByCustomerAndProduct(){
        Integer customerId = 5;
        Integer productId = 1;
        Long count = repo.countByCustomerAndProduct(customerId, productId);

        assertThat(count).isEqualTo(1);
    }
}
