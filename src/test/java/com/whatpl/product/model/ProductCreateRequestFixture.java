package com.whatpl.product.model;

import java.time.LocalDate;
import java.util.List;

import com.whatpl.global.common.domain.enums.Skill;
import com.whatpl.product.request.ProductCreateRequest;

public class ProductCreateRequestFixture {
	private ProductCreateRequestFixture() {};
	
	public static ProductCreateRequest create(boolean icon) {
		return ProductCreateRequest.builder()
				.title("제목테스트")
				.content("내용테스트")
				.icon(icon ? "/testIcon" : null)
				.oneLine("한줄소개")
				.emailForUt("ijhghji@gmail.com")
				.androidRink("and")
				.iosRink("ios")
				.webRink("https://ww")
				.skills(List.of(Skill.JAVA, Skill.FIGMA))
				.build();
	}
}
