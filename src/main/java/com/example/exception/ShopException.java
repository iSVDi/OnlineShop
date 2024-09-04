package com.example.exception;

public class ShopException extends RuntimeException {

    public ShopException(ShopExceptionTitle title) {
        super(title.name());
    }
}
