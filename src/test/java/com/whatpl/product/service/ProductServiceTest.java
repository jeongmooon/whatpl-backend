package com.whatpl.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.whatpl.member.domain.Member;
import com.whatpl.member.model.MemberFixture;
import com.whatpl.member.repository.MemberRepository;
import com.whatpl.product.domain.Product;
import com.whatpl.product.repository.ProductRepository;
import com.whatpl.product.request.ProductCreateRequest;
import com.whatpl.product.request.ProductUpdateRequest;
import com.whatpl.product.response.ProductGetResponse;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@Mock
	ProductRepository productRepository;
	
	@Mock
    private MemberRepository memberRepository;
	
	@InjectMocks
	ProductService productService;
	
	@Test
	void create() {
		
		ProductCreateRequest cpr = ProductCreateRequest.builder()
				.title("제목")
				.icon(null)
				.content("내용")
				.emailForUt("a@a.a")
				.androidRink("/df")
				.iosRink("/ss")
				.webRink("/dd")
				.build();
		Member writer = MemberFixture.onlyRequired();
		Product product = cpr.toEntity(writer);
		
		List<MultipartFile> productPictures = List.of(
                new MockMultipartFile("testFile1", "testFile1".getBytes()),
                new MockMultipartFile("testFile2", "testFile2".getBytes()));
		
//		when(productService.createProduct(any()))
//							.thenReturn(Utils.makeUuid(false));
		when(productRepository.save(product))
							.thenReturn(product);
		when(productRepository.findById(any()))
							.thenReturn(Optional.of(product));
		

		//ProductService productService = new ProductService(productRepository);
		Long id = productService.createProduct(cpr, productPictures, 1L);
		
		Optional<Product> retProductOptional = productRepository.findById(id);
		
		assertTrue(retProductOptional.isPresent());
		Product retProduct = retProductOptional.get();
		assertEquals(product.getId(), retProduct.getId());
		assertEquals(product.getTitle(), retProduct.getTitle());
	}
	
	@Test
	void getProduct() {
		Long id = (long) 1;
		Product product = Product.builder().id(id).views(0).build();
		when(productRepository.findById(id))
								.thenReturn(Optional.of(product));
		
		ProductGetResponse productDTO = productService.getProduct(id);
		
		assertEquals(productDTO.getViews(), 1);
	}
	
	@Test
	void updateProduct() {
		Long id = (long) 1;
		Product product = Product.builder().id(id).title("제목").content("내용").build();
		ProductUpdateRequest request = ProductUpdateRequest.builder().title("제목바뀜").content("내용바뀜").build();
		
		when(productRepository.findById(id))
								.thenReturn(Optional.of(product));
		
		productService.updateProduct(id, request, 1L);
		
		assertEquals(product.getTitle(),request.getTitle());
		assertEquals(product.getContent(),request.getContent());
	}
}
