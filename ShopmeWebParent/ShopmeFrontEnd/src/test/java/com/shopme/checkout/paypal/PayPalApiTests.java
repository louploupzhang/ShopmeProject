package com.shopme.checkout.paypal;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class PayPalApiTests {
    public static final String BASE_URL = "https://api.sandbox.paypal.com";
    public static final String GET_ORDER_API = "/v2/checkout/orders/";
    public static final String CLIENT_ID = "AUxJk_oHZ4MVQ3FCuCzuXi7OasziSLx7BWJMf_45eKx6ed56T7hvmAdzux7PhZw3INxEh1wP3yrip8wa";
    public static final String CLIENT_SECRET = "EACBLiZqHbBu3ii0-FWOOvKV67oIm12Kk4SjDi4BGAUF5WQzqQ0n2afnADfdsR6q3gk5flR9i71fQQjN";

    @Test
    public void testGetOrderDetails() {
        String orderId = "4XX261076K316912U";
        String requestURL = BASE_URL + GET_ORDER_API + orderId;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Accept-Language", "en_US");
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PayPalOrderResponse> response = restTemplate.exchange(requestURL, HttpMethod.GET, request, PayPalOrderResponse.class);
        PayPalOrderResponse orderResponse = response.getBody();

        System.out.println("Order ID: " + orderResponse.getId());
        System.out.println("Validated: " + orderResponse.validate(orderId));
    }
}
