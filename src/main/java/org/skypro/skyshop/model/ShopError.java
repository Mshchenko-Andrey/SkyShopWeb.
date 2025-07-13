package org.skypro.skyshop.model;

public record ShopError(String code, String message) {
    public static ShopError of(String code, String message) {
        return new ShopError(code, message);
    }
}