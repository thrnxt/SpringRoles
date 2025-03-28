package org.thr.crudrest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.thr.crudrest.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}
