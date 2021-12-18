package com.syqu.shop.service;

import com.syqu.shop.creator.UserCreator;
import com.syqu.shop.object.Customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @MockBean
    private CustomerService userService;

    @Test
    public void checkIfUserServiceIsNotNull(){
        initMocks(this);

        assertThat(userService).isNotNull();
    }

    @Test
    public void saveUserTests(){
        Customer user = UserCreator.createTestUser();
        userService.save(user);
        when(userService.findById(user.getId())).thenReturn(user);
        Customer found = userService.findById(user.getId());

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void whenFindByIdThenReturnUser() {
        when(userService.findById(100L)).thenReturn(UserCreator.createTestUser());
        Customer found = userService.findById(100L);

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(UserCreator.EMAIL);
    }

    @Test
    public void whenFindByUsernameThenReturnUser() {
        initMocks(this);

        when(userService.findByEmail(UserCreator.EMAIL)).thenReturn(UserCreator.createTestUser());
        Customer found = userService.findByEmail(UserCreator.EMAIL);

        assertThat(found.getEmail()).isEqualTo(UserCreator.EMAIL);
    }

    @Test
    public void whenFindByEmailThenReturnUser() {
        initMocks(this);

        when(userService.findByEmail(UserCreator.EMAIL)).thenReturn(UserCreator.createTestUser());
        Customer found = userService.findByEmail(UserCreator.EMAIL);

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(UserCreator.EMAIL);
    }

    @Test
    public void whenFindByIdAndNoUserThenReturnNull(){
        Customer found = userService.findById(25L);

        assertThat(found).isNull();
    }

    @Test
    public void whenFindByUsernameAndNoUserThenReturnNull(){
        Customer found = userService.findByEmail("Tests");

        assertThat(found).isNull();
    }

    @Test
    public void whenFindByEmailAndNoUserThenReturnNull(){
        Customer found = userService.findByEmail("example@donut.org");

        assertThat(found).isNull();
    }
}
