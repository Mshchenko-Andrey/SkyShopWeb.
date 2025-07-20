package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @Test
    void addProduct_WhenProductNotExists_ThrowsException() {
        UUID productId = UUID.randomUUID();
        when(storageService.getProductById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class,
                () -> basketService.addProduct(productId));

        verify(storageService).getProductById(productId);
        verifyNoInteractions(productBasket);
    }

    @Test
    void addProduct_WhenProductExists_AddsToBasket() {
        UUID productId = UUID.randomUUID();
        Product product = new SimpleProduct(productId, "Test", 100);
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        basketService.addProduct(productId);

        verify(storageService).getProductById(productId);
        verify(productBasket).addProduct(productId);
    }

    @Test
    void getUserBasket_WhenBasketEmpty_ReturnsEmptyBasket() {
        when(productBasket.getProducts()).thenReturn(Collections.emptyMap());

        UserBasket result = basketService.getUserBasket();

        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotal());
        verify(productBasket).getProducts();
        verifyNoInteractions(storageService);
    }

    @Test
    void getUserBasket_WhenBasketHasItems_ReturnsCorrectBasket() {
        UUID productId = UUID.randomUUID();
        Product product = new SimpleProduct(productId, "Test", 100);
        Map<UUID, Integer> basketContent = Map.of(productId, 2);

        when(productBasket.getProducts()).thenReturn(basketContent);
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        UserBasket result = basketService.getUserBasket();

        assertEquals(1, result.getItems().size());
        BasketItem item = result.getItems().get(0);
        assertEquals(product, item.getProduct());
        assertEquals(2, item.getQuantity());
        assertEquals(200, result.getTotal());

        verify(productBasket).getProducts();
        verify(storageService).getProductById(productId);
    }

    @Test
    void getUserBasket_WhenProductInBasketNotFound_ThrowsException() {
        UUID productId = UUID.randomUUID();
        Map<UUID, Integer> basketContent = Map.of(productId, 1);

        when(productBasket.getProducts()).thenReturn(basketContent);
        when(storageService.getProductById(productId)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class,
                () -> basketService.getUserBasket());
    }
}