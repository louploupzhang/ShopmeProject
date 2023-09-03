package com.shopme.admin.shippingrate;

import com.shopme.admin.paging.SearchRepository;
import com.shopme.common.entity.ShippingRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ShippingRateRepository extends SearchRepository<ShippingRate, Integer> {
    @Query("select sr from ShippingRate sr where sr.country.id = ?1 and sr.state = ?2")
    ShippingRate findByCountryAndState(Integer countryId, String state);

    @Query("update ShippingRate sr set sr.codSupported = ?2 where sr.id = ?1")
    @Modifying
    void updateCODSupport(Integer id, boolean enabled);

    @Query("select sr from ShippingRate sr where sr.country.name like %?1% or sr.state like %?1%")
    Page<ShippingRate> findAll(String keyword, Pageable pageable);

    Long countById(Integer id);
}
