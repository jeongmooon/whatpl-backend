package com.whatpl.product.request;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.whatpl.global.common.domain.enums.Skill;
import com.whatpl.member.domain.Member;
import com.whatpl.product.domain.Product;
import com.whatpl.product.domain.enums.ProductStatus;
import com.whatpl.product.validator.AppDownloadLink;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateRequest {
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
	private List<Skill> skills;
	
	public Product toEntity(Member writer) {
		return Product.builder()
				.title(this.getTitle())
				.icon(this.getIcon() == null ? "/whatpl" : this.getIcon())
				.content(this.getContent())
				.emailForUt(this.getEmailForUt())
				.androidRink(this.getAndroidRink())
				.iosRink(this.getIosRink())
				.webRink(this.getWebRink())
				.status(ProductStatus.PUBLISHED)
				.writer(writer)
				.build();
	}
}
