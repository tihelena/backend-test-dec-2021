package com.geekbrains.backend.test.miniMarket;

import com.geekbrains.backend.test.miniMarket.model.Product;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;




public class MiniMarketService {


    private final MiniMarketApi api;


    public MiniMarketService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://minimarket1.herokuapp.com/market/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MiniMarketApi.class);
    }

    public List<Product> getProducts() throws IOException {
        return api.getProducts()
                .execute()
                .body();
    }

    public Product getProduct(long id) throws IOException {
        Response<Product> response = api.getProduct(id).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new RuntimeException(response.errorBody().string());
        }
    }

//    public Long createProduct(Product product) throws IOException {
//        return api.createProduct(product)
//                .execute()
//                .body()
//                .getId();
//    }

    public Product deleteProduct(long id) throws IOException {
        api.deleteProduct(id).execute();
        return null;
    }

    public void updateProduct(Product product) throws IOException {
        api.updateProduct(product).execute();
    }






}