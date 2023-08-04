package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

import java.util.List;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userLouploup = new User("zhang.sy@hotmail.com", "louploup", "Shuyuan", "Zhang");
        userLouploup.addRole(roleAdmin);

        User savedUser = repo.save(userLouploup);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRole() {
        User userTejia = new User("zhang.tj@163.com", "ztj", "Tejia", "Zhang");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);
        userTejia.addRole(roleEditor);
        userTejia.addRole(roleAssistant);

        User savedUser = repo.save(userTejia);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        User userLouploup = repo.findById(1).get();
        System.out.println(userLouploup);
        assertThat(userLouploup).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User userLouploup = repo.findById(1).get();
        userLouploup.setEnabled(true);
        userLouploup.setEmail("zhang.sy@hotmail.com");

        repo.save(userLouploup);
    }

    @Test
    public void testUpdateUserRoles() {
        User userTejia = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);
        userTejia.getRoles().remove(roleEditor);
        userTejia.addRole(roleSalesperson);

        repo.save(userTejia);
    }

    @Test
    public void testDeleteUser() {
        Integer userId = 2;
        repo.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "zsy.joshua@gmail.com";
        User user = repo.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer id = 1;
        Long countById = repo.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisableUser() {
        Integer id = 1;
        repo.updateEnabledStatus(id, false);
    }

    @Test
    public void testEnableUser() {
        Integer id = 1;
        repo.updateEnabledStatus(id, true);
    }

    @Test
    public void testListFirstPage() {
        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repo.findAll(pageable);

        List<User> listUsers = page.getContent();

        listUsers.forEach(user -> System.out.println(user));

        assertThat(listUsers.size()).isEqualTo(pageSize);
    }

    @Test
    public void testSearchUsers() {
        String keyword = "bruce";

        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repo.findAll(keyword, pageable);

        List<User> listUsers = page.getContent();

        listUsers.forEach(user -> System.out.println(user));

        assertThat(listUsers.size()).isGreaterThan(0);
    }
}
