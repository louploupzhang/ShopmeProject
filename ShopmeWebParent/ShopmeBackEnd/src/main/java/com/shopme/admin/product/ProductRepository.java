package com.shopme.admin.product;

import com.shopme.common.entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    public Long countById(Integer id);

    public Product findByName(String name);

    @Query("update Product p set p.enabled = ?2 where p.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);
}
