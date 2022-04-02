package com.eticaret;

import com.eticaret.repository.ProductRepository;
import com.eticaret.repository.entity.Product;

public class Runner {
    public static void main(String[] args) {
        new ProductRepository().save(new Product("Bilgisayar",12544));
    }
}
