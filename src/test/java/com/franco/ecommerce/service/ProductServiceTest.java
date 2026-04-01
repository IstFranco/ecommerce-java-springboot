package com.franco.ecommerce.service;

import com.franco.ecommerce.model.Product;
import com.franco.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_DuplicateName_ThrowsException() {
        //GIVEN
        Product fakeProduct = new Product();
        fakeProduct.setProductId(1L);
        fakeProduct.setName("FakeCoca");
        fakeProduct.setPrice(10.0);
        fakeProduct.setStock(15);

        //WHEN
        when(productRepository.existsByName("FakeCoca")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(fakeProduct);
        });

        assertEquals("A product with name: " + fakeProduct.getName() + " already exists.", exception.getMessage());

        verify(productRepository, never()).save(any());
    }

    @Test
    void createProduct_Succes() {
        Product fakeProduct = new Product();
        fakeProduct.setProductId(1L);
        fakeProduct.setName("FakeCoca");
        fakeProduct.setPrice(10.0);
        fakeProduct.setStock(15);

        when(productRepository.existsByName("FakeCoca")).thenReturn(false);
        when(productRepository.save(fakeProduct)).thenReturn(fakeProduct);

        assertEquals(fakeProduct, productService.createProduct(fakeProduct));

        verify(productRepository, times(1)).save(fakeProduct);
    }
}
