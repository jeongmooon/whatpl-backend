package com.whatpl.product.model;

import java.util.List;

import com.whatpl.global.common.domain.enums.Skill;
import com.whatpl.product.domain.Product;

public class ProductFixture {
	public static Product create(boolean icon) {
		Product product = Product.builder()
							.title("제목테스트")
							.content("내용테스트")
							.icon(icon ? "/testIcon" : "/wathpl")
							.oneLine("한줄소개")
							.emailForUt("ijhghji@gmail.com")
							.androidRink("and")
							.iosRink("ios")
							.webRink("https://ww")
							.build();
		
		product.updateProductSkills(List.of(Skill.JAVA, Skill.FIGMA));
		
		return product;
	}
}
