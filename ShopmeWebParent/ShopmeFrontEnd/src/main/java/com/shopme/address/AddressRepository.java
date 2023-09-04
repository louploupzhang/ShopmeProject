package com.shopme.address;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address,Integer> {
    List<Address> findByCustomer(Customer customer);

    @Query("select a from Address a where a.id = ?1 and a.customer.id = ?2")
    Address findByIdAndCustomer(Integer addressId, Integer customerId);

    @Query("delete from Address a where a.id = ?1 and a.customer.id = ?2")
    @Modifying
    void deleteByIdAndCustomer(Integer addressId, Integer customerId);

    @Query("update Address a set a.defaultForShipping = true where a.id = ?1")
    @Modifying
    void setDefaultAddress(Integer id);

    @Query("update Address a set a.defaultForShipping = false where a.id != ?1 and a.customer.id = ?2")
    @Modifying
    void setNonDefaultForOthers(Integer defaultAddressId, Integer customerId);
}
