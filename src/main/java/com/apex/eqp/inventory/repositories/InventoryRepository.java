package com.apex.eqp.inventory.repositories;

import com.apex.eqp.inventory.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InventoryRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select p.* from Product p left outer join " +
            "Recalled_Product rp " +
            "on rp.name=p.name WHERE rp.name <> p.name",
            nativeQuery = true)
    List<Product> getProductsThatAreNotRecalled();
}
