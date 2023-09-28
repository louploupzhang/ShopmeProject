package com.shopme.product;

import com.shopme.common.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    @Query("select p from Product p where p.enabled = true and (p.category.id = ?1 or p.category.allParentIDs like %?2%) order by p.name asc")
    public Page<Product> listByCategory(Integer categoryId, String categoryIDMatch, Pageable pageable);

    public Product findByAlias(String alias);

    @Query(value = "select * from products where enabled = true and match(name, short_description, full_description) against (?1)",
            nativeQuery = true)
    public Page<Product> search(String keyword, Pageable pageable);

    @Query("update Product p set p.averageRating = coalesce((select avg(r.rating) from Review r where  r.product.id = ?1), 0), "
            + "p.reviewCount = (select count(r.id) from Review r where r.product.id = ?1) where p.id = ?1")
    @Modifying
    void updateReviewCountAndAverageRating(Integer productId);
}
