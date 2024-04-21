package com.whatpl.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whatpl.product.domain.Product;
import com.whatpl.product.domain.enums.ProductStatus;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByTitleId(String titleId);
	List<Product> findByTitleOrContentContainingAndStatus(String title, String contnet, ProductStatus status);
}
