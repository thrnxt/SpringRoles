package org.thr.crudrest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.thr.crudrest.model.Product;
import org.thr.crudrest.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Просмотр всех продуктов (доступно всем аутентифицированным пользователям)
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Добавление нового продукта (доступно только ADMIN)
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product, Authentication authentication) {
        if (!hasRole(authentication, "ADMIN")) {
            return ResponseEntity.status(403).body("Доступ запрещен!");
        }
        productRepository.save(product);
        return ResponseEntity.ok("Продукт добавлен!");
    }

    // Удаление продукта по ID (доступно только ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id, Authentication authentication) {
        if (!hasRole(authentication, "ROLE_ADMIN")) {
            return ResponseEntity.status(403).body("Доступ запрещен!");
        }

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            return ResponseEntity.ok("Продукт удален!");
        } else {
            return ResponseEntity.status(404).body("Продукт не найден!");
        }
    }

    private boolean hasRole(Authentication authentication, String role) {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return roles.contains("ROLE_" + role); // Оставить добавление "ROLE_" только здесь
    }
}
