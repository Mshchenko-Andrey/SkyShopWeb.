package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BasketService {
    private final ProductBasket basket;
    private final StorageService storageService;

    public BasketService(ProductBasket basket, StorageService storageService) {
        this.basket = basket;
        this.storageService = storageService;
    }

    public void addProduct(UUID productId) {
        Product product = storageService.getProductOrThrow(productId);
        basket.addProduct(productId);
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> basketProducts = basket.getProducts();

        List<BasketItem> items = basketProducts.entrySet().stream()
                .map(entry -> {
                    Product product = storageService.getProductOrThrow(entry.getKey());
                    return new BasketItem(product, entry.getValue());
                })
                .collect(Collectors.toList());

        return new UserBasket(items);
    }
}