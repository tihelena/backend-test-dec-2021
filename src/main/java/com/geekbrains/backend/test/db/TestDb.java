package com.geekbrains.backend.test.db;

import com.geekbrains.db.dao.ProductsMapper;
import com.geekbrains.db.model.Products;
import com.geekbrains.db.model.ProductsExample;

import java.util.List;

public class TestDb {
    public static void main(String[] args) {

        DbService dbService = new DbService();
        ProductsMapper productsMapper = dbService.getProductsMapper();
        Products product = productsMapper.selectByPrimaryKey(1L);
        System.out.println(product);

        Products forCreate = new Products();
        forCreate.setTitle("Coca cola");
        forCreate.setPrice(50);
        forCreate.setCategoryId(1L);

       productsMapper.insert(forCreate);

        ProductsExample filter = new ProductsExample();

        List<Products> products = productsMapper.selectByExample(filter);
        System.out.println(products);

    }

}
