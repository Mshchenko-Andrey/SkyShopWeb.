package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.*;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> products = new HashMap<>();
    private final Map<UUID, Article> articles = new HashMap<>();

    public StorageService() {
        initializeTestData();
    }

    private void initializeTestData() {
        UUID productId1 = UUID.randomUUID();
        products.put(productId1, new SimpleProduct(productId1, "Java Book", 500));

        UUID productId2 = UUID.randomUUID();
        products.put(productId2, new DiscountedProduct(productId2, "Java Course", 10000, 10));

        UUID productId3 = UUID.randomUUID();
        products.put(productId3, new FixPriceProduct(productId3, "USB Cable"));

        UUID articleId1 = UUID.randomUUID();
        articles.put(articleId1, new Article(articleId1, "Java News", "Latest Java features"));

        UUID articleId2 = UUID.randomUUID();
        articles.put(articleId2, new Article(articleId2, "Spring Update", "New Spring features"));
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public Product getProductOrThrow(UUID id) {
        return getProductById(id)
                .orElseThrow(() -> new NoSuchProductException("Товар с ID " + id + " не найден"));
    }

    public Collection<Product> getAllProducts() {
        return Collections.unmodifiableCollection(products.values());
    }

    public Collection<Article> getAllArticles() {
        return Collections.unmodifiableCollection(articles.values());
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> result = new ArrayList<>();
        result.addAll(products.values());
        result.addAll(articles.values());
        return result;
    }
}