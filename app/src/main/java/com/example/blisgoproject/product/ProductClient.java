package com.example.blisgoproject.product;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductClient {

    private static ProductClient instance;
    private ProductService productService;

    public ProductClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.100.102.14:8999/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productService = retrofit.create(ProductService.class);
    }

    public static  ProductClient getInstance() {
        if (instance == null) {
            instance = new ProductClient();
        }
        return instance;
    }

    public ProductService getProductService() { return productService; }

}
