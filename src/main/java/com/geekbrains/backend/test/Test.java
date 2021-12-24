package com.geekbrains.backend.test;

import com.geekbrains.backend.test.miniMarket.MiniMarketService;
import com.geekbrains.backend.test.miniMarket.model.Product;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class Test {
    public static void main(String[] args)throws IOException {
        MiniMarketService service = new MiniMarketService();
        Product product = service.getProduct(4L);
        System.out.println(product);



//        service.deleteProduct(99L);
//        System.out.println("Yes!");







    }





}
