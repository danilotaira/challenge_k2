package com.supermarket.code.basket.services;

import com.supermarket.code.basket.DTO.ProductDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class ProductService {

    final String API_PRODUCT = "http://localhost:8081/products";
    private static final ParameterizedTypeReference<ProductDTO> PRODUCT = ParameterizedTypeReference.forType(ProductDTO.class);
    private static final ParameterizedTypeReference<List<ProductDTO>> LIST_PRODUCT = new ParameterizedTypeReference<>() {};

    private RestTemplate restTemplate;

    public ProductService() {
        restTemplate = new RestTemplate();
    }

    public List<ProductDTO> getList(){
        return execute(API_PRODUCT, LIST_PRODUCT);
    }

    public ProductDTO getProduct(String id){
        return execute(API_PRODUCT + "/" + id, PRODUCT);
    }

    private <T> T execute(String url, ParameterizedTypeReference<T> responseType) {
        final URI uri = UriComponentsBuilder.fromUriString(url)
                .build()
                .toUri();
        RequestEntity<Object> requestEntity = new RequestEntity<>(null, null, HttpMethod.GET, uri);
        ResponseEntity<T> response;

        try {
            response = restTemplate.exchange(requestEntity, responseType);
        } catch (Exception e) {
            return null;
        }
        return response.getBody();
    }
}
