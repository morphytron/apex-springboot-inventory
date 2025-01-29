package com.apex.eqp.inventory.services;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.entities.RecalledProduct;
import com.apex.eqp.inventory.helpers.ProductFilter;
import com.apex.eqp.inventory.repositories.InventoryRepository;
import com.apex.eqp.inventory.repositories.RecalledProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final InventoryRepository inventoryRepository;
    @Autowired
    private final RecalledProductRepository recalledProductRepository;

    @Autowired
    private final RecalledProductService recalledProductService;

    @Transactional
    public Product save(Product product) {
        return inventoryRepository.save(product);
    }

    public Collection<Product> getAllProduct() {
        Collection<RecalledProduct> recalledProducts =
                recalledProductService.getAllRecalledProducts();
        HashSet<String> productNames = new HashSet<>();
        recalledProducts.stream().forEach(rp -> {
            productNames.add(rp.getName());
        });
        ProductFilter filter = new ProductFilter(productNames);

        return filter.removeRecalledFrom(inventoryRepository.findAll());
    }

    public Optional<Product> findById(Integer id) {
        return inventoryRepository.findById(id);
    }
}

/**
 * The candidate needs to solve the challenge using Test Driven Development (TDD)
 * They should have downloaded the project before hand and get themselves familiar with it
 *
 * The project has 2 APIs, one for getAllProducts and getAllRecalledProducts
 * Challenge:
 * -Fix the code so that the getAllProducts API (productService.getAllProduct() ) returns only those Products that are not also RecalledProducts
 * -Use TDD if possible
 * -Put test case(s) in ProductServiceTests.java at bottom of it
 * -resources/data.sql has an H2 data file you can use for testing.
 * -Communication is important - talk your solution out
 */
