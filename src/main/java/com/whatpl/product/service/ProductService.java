package com.whatpl.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whatpl.global.exception.BizException;
import com.whatpl.global.exception.ErrorCode;
import com.whatpl.member.domain.Member;
import com.whatpl.member.repository.MemberRepository;
import com.whatpl.product.domain.Product;
import com.whatpl.product.domain.ProductPicture;
import com.whatpl.product.domain.enums.ProductStatus;
import com.whatpl.product.repository.ProductRepository;
import com.whatpl.product.request.ProductCreateRequest;
import com.whatpl.product.request.ProductUpdateRequest;
import com.whatpl.product.response.ProductGetResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final MemberRepository memberRepository;	
	private final ProductRepository productRepository;
	private final ProductAttachmentService productAttachmentService;
	
	public Long createProduct(ProductCreateRequest request, List<MultipartFile> picutres, Long memberId) {
		Member writer = memberRepository.findById(memberId)
                .orElseThrow(() -> new BizException(ErrorCode.NOT_FOUND_MEMBER));
		List<ProductPicture> productPictures = productAttachmentService.uploadProductPicture(picutres);
		Product product= request.toEntity(writer);
		product.updateProductSkills(request.getSkills());
		product.updateProductPictures(productPictures);
		return productRepository.save(product).getId();
	}
	
	public ProductGetResponse getProduct(Long Id) {
		Product product = productRepository.findById(Id)
							.orElseThrow(() -> new BizException(ErrorCode.NOT_FOUND_PRODUCT));
		// 조회수 증가
		product.increaseViews();
		
		return ProductGetResponse.ofEntity(product);
	}
	
	@Transactional
	public void updateProduct(Long id, ProductUpdateRequest request, Long memberId) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new BizException(ErrorCode.NOT_FOUND_PRODUCT));
		
		if(product.getWriter().getId() != memberId) {
			new BizException(ErrorCode.NOT_AUTHOR_OF_PRODUCT);
		}
		
		product.update(request);
	}
	
	public List<ProductGetResponse> getProductList(String keyword) {
		return productRepository.findByTitleOrContentContainingAndStatus(keyword, keyword, ProductStatus.PUBLISHED)
				.stream().map(ProductGetResponse::ofEntity).collect(Collectors.toList());
	}
}
