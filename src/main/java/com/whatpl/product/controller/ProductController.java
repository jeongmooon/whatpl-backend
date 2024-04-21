package com.whatpl.product.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whatpl.global.security.domain.MemberPrincipal;
import com.whatpl.product.request.ProductCreateRequest;
import com.whatpl.product.request.ProductUpdateRequest;
import com.whatpl.product.response.ProductGetResponse;
import com.whatpl.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProductController {
	
	private final ProductService productService;
	
	@PostMapping("/product")
	public ResponseEntity<String> createProducet(@RequestPart ProductCreateRequest request,
												@RequestPart(required = false) List<MultipartFile> picutres,
												@AuthenticationPrincipal MemberPrincipal principal){
		Long productId = productService.createProduct(request, picutres, principal.getId());
		String uri = String.format("/product/%d", productId);
		return ResponseEntity.ok(URI.create(uri).toString());
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<ProductGetResponse> getProduct(@PathVariable Long productId) {
		ProductGetResponse productGetResponse = productService.getProduct(productId);
		return ResponseEntity.ok(productGetResponse);
	}
	
	@PatchMapping("/product/{productId}")
	public ResponseEntity<String> updateProduct(@PathVariable Long productId,
												@RequestBody ProductUpdateRequest request,
												@AuthenticationPrincipal MemberPrincipal principal){
		productService.updateProduct(productId, request, principal.getId());
		String uri = String.format("/product/%d", productId);
		return ResponseEntity.ok(URI.create(uri).toString());
	}
}
