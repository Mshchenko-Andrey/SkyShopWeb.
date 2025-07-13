package org.skypro.skyshop.model.article;

import org.skypro.skyshop.model.search.Searchable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import java.util.Objects;

public class Article implements Searchable {
    private final UUID id;
    private final String title;
    private final String text;

    public Article(UUID id, String title, String text) {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Заголовок статьи не может быть пустым");
        }
        this.id = id;
        this.title = title;
        this.text = text;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public String getName() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return title + " " + text;
    }

    @JsonIgnore
    @Override
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public String toString() {
        return title + "\n" + text;
    }

    @Override
    public String getStringRepresentation() {
        return getName() + " — " + getContentType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

