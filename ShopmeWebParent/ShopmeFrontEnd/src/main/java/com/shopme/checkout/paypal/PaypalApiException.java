package com.shopme.checkout.paypal;

public class PaypalApiException extends Throwable {
    public PaypalApiException(String message) {
        super(message);
    }
}
