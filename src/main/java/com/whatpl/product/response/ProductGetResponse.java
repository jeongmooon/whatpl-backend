package com.whatpl.product.response;

import java.util.ArrayList;
import java.util.List;

import com.whatpl.product.domain.Product;
import com.whatpl.product.domain.ProductMember;
import com.whatpl.product.domain.enums.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductGetResponse {
	private Long id;
	
	private String title;
	private String icon;
	private String content;
	private int views;
	private String emailForUt;
	private String androidRink;
	private String iosRink;
	private String webRink;
	private ProductStatus status;
	private List<ProductMember> ProductMembers = new ArrayList<>();
	
	public static ProductGetResponse ofEntity(Product product) {
		return ProductGetResponse.builder()
				.id(product.getId())
				.title(product.getTitle())
				.icon(product.getIcon())
				.content(product.getContent())
				.views(product.getViews())
				.emailForUt(product.getEmailForUt())
				.androidRink(product.getAndroidRink())
				.iosRink(product.getIosRink())
				.webRink(product.getWebRink())
				.status(product.getStatus())
				.ProductMembers(product.getProductMembers())
				.build();
	}
}
