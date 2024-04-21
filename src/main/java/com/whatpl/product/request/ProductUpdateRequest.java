package com.whatpl.product.request;

import org.hibernate.validator.constraints.Length;

import com.whatpl.product.domain.Product;
import com.whatpl.product.domain.enums.ProductStatus;
import com.whatpl.product.validator.AppDownloadLink;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductUpdateRequest {
	private String titleId;
	private String title;
	private String icon;
	private String content;
	@Length(max=20, message = "한줄 소개는 20자 까지 입력이 가능합니다")
	private String oneLine;
	private String emailForUt;
	@NotNull
	@AppDownloadLink
	private String androidRink;
	@NotNull
	@AppDownloadLink
	private String iosRink;
	private String webRink;
	private ProductStatus status;
	
	public Product toEntity() {
		return Product.builder()
				.title(this.getTitle())
				.icon(this.getIcon() == null ? "/whatpl" : this.getIcon())
				.content(this.getContent())
				.oneLine(this.getOneLine())
				.emailForUt(this.getEmailForUt())
				.androidRink(this.getAndroidRink())
				.iosRink(this.getIosRink())
				.webRink(this.getWebRink())
				.status(this.getStatus())
				.build();
	}
}
