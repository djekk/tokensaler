package com.syqu.shop.service;

import com.syqu.shop.object.Product;

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
public class ProductServiceTests {

    @MockBean
    private ProductService productService;

    @Test
    public void checkIfProductServiceIsNotNull(){
        initMocks(this);

        assertThat(productService).isNotNull();
    }

    @Test
    public void whenCountThenReturnProductsCount(){
        when(productService.count()).thenReturn(3L);
        long count = productService.count();

        assertThat(count).isNotNegative();
        assertThat(count).isEqualTo(3L);

    }

    @Test
    public void whenFindByIdAndNoProductThenReturnNull(){
        Product found = productService.findById(50L);

        assertThat(found).isNull();
    }
}
