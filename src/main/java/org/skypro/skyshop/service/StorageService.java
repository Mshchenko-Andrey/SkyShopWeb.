package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.*;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();
        initializeTestData();
    }

    private void initializeTestData() {

        products.put(UUID.randomUUID(), new SimpleProduct(
                UUID.randomUUID(), "Java Book", 500));
        products.put(UUID.randomUUID(), new DiscountedProduct(
                UUID.randomUUID(), "Java Course", 10000, 10));
        products.put(UUID.randomUUID(), new FixPriceProduct(
                UUID.randomUUID(), "USB Cable"));


        articles.put(UUID.randomUUID(), new Article(
                UUID.randomUUID(), "Java News", "Latest Java features"));
        articles.put(UUID.randomUUID(), new Article(
                UUID.randomUUID(), "Spring Update", "New Spring features"));
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
