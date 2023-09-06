package com.shopme.address;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AddressRepositoryTests {
    @Autowired
    private AddressRepository repo;

    @Test
    public void testAddNew() {
        Integer customerId = 5;
        Integer countryId = 234; //USA

        Address newAddress = new Address();
        newAddress.setCustomer(new Customer(customerId));
        newAddress.setCountry(new Country(countryId));
        newAddress.setFirstName("Tobie");
        newAddress.setLastName("Abel");
        newAddress.setPhoneNumber("1919129432");
        newAddress.setAddressLine1("4232 Gordon Street");
        newAddress.setAddressLine2("Novak Building");
        newAddress.setCity("Chino");
        newAddress.setState("California");
        newAddress.setPostalCode("91730");

        Address savedAddress = repo.save(newAddress);

        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindByCustomer() {
        Integer customerId = 5;
        List<Address> listAddresses = repo.findByCustomer(new Customer(customerId));

        assertThat(listAddresses.size()).isGreaterThan(0);
        listAddresses.forEach(System.out::println);
    }

    @Test
    public void testFindByIdAndCustomer() {
        Integer addressId = 1;
        Integer customerId = 5;
        Address address = repo.findByIdAndCustomer(addressId, customerId);

        assertThat(address).isNotNull();
        System.out.println(address);
    }

    @Test
    public void testUpdate() {
        Integer addressId = 2;
//        String phoneNumber = "333-232-3902";
        Address address = repo.findById(addressId).get();
//        address.setPhoneNumber(phoneNumber);
        address.setDefaultForShipping(true);

        Address updatedAddress = repo.save(address);
//        assertThat(updatedAddress.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    public void testDeleteByIdAndCustomer() {
        Integer addressId = 1;
        Integer customerId = 5;

        repo.deleteByIdAndCustomer(addressId, customerId);

        Address address = repo.findByIdAndCustomer(addressId, customerId);
        assertThat(address).isNull();
    }

    @Test
    public void testSetDefault() {
        Integer addressId = 4;
        repo.setDefaultAddress(addressId);

        Address address = repo.findById(addressId).get();
        assertThat(address.isDefaultForShipping()).isTrue();
    }

    @Test
    public void testSetNonDefaultAddresses() {
        Integer addressId = 4;
        Integer customerId = 5;
        repo.setNonDefaultForOthers(addressId, customerId);
    }

    @Test
    public void testGetDefault() {
        Integer customerId = 5;
        Address address = repo.findDefaultByCustomer(customerId);
        assertThat(address).isNotNull();
        System.out.println(address);
    }
}
