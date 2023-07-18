package com.kits.ecommerce.exeptions;

public class UserAlreadyExistsException  extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
