package com.syqu.shop.repository;

import com.syqu.shop.creator.UserCreator;
import com.syqu.shop.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository userRepository;

    @Test
    public void checkIfUserRepositoryIsNotNull(){
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void checkIfParamsAreTheSame(){
        Customer testObject = UserCreator.createTestUser();
        entityManager.persistAndFlush(testObject);

        Customer found = userRepository.findByUsername(testObject.getUsername());

        assertThat(found.getId()).isEqualTo(testObject.getId());
        assertThat(found.getUsername()).isEqualTo(testObject.getUsername());
        assertThat(found.getPassword()).isEqualTo(testObject.getPassword());
        assertThat(found.getPasswordConfirm()).isEqualTo(found.getPassword());
        assertThat(found.getPasswordConfirm()).isEqualTo(testObject.getPasswordConfirm());
        assertThat(found.getEmail()).isEqualTo(testObject.getEmail());
    }

    @Test
    public void whenFindByEmailThenReturnUser(){
        Customer testObject = UserCreator.createTestUser();

        entityManager.persistAndFlush(testObject);

        Customer found = userRepository.findByEmail(testObject.getEmail());
        assertThat(found.getEmail()).isEqualTo(testObject.getEmail());
    }

    @Test
    public void whenFindByIdThenReturnUser(){
        Customer testObject = UserCreator.createTestUser();

        entityManager.persistAndFlush(testObject);

        Customer found = userRepository.findById(testObject.getId());
        assertThat(found.getId()).isEqualTo(testObject.getId());
    }

    @Test
    public void whenFindByIdAndNoUserThenReturnNull() {
        assertThat(userRepository.findById(new Random().nextLong())).isNull();
    }

    @Test
    public void whenFindByUsernameAndNoUserThenReturnNull() {
        assertThat(userRepository.findByUsername("xxminecraftplayerxx")).isNull();
    }

    @Test
    public void whenFindByEmailAndNoUserThenReturnNull() {
        assertThat(userRepository.findByEmail("whatis@going.on")).isNull();
    }
}
