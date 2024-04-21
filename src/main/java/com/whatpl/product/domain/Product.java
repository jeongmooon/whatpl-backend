package com.whatpl.product.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.CollectionUtils;

import com.whatpl.global.common.BaseTimeEntity;
import com.whatpl.global.common.domain.enums.Skill;
import com.whatpl.member.domain.Member;
import com.whatpl.product.domain.enums.ProductStatus;
import com.whatpl.product.request.ProductUpdateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicUpdate
@Table(name = "product")
public class Product extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String icon;
	private String oneLine;
	private String content;
	private int views;
	
	@Column(nullable = true)
	private String emailForUt;
	
	@Column(nullable = true)
	private String androidRink;
	
	@Column(nullable = true)
	private String iosRink;
	
	@Column(nullable = true)
	private String webRink;
	
	@Enumerated(EnumType.STRING)
	private ProductStatus status;
	
	@OneToMany(mappedBy = "product")
	private List<ProductMember> productMembers = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	private List<ProductPicture> productPictures = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	private List<ProductSkill> productSkills = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
	private Member writer;
	
	@Builder
	public Product(Long id, String title, String icon, String oneLine, String content, String emailForUt, String androidRink, String iosRink, String webRink, int views, ProductStatus status, Member writer) {
		this.id = id;
		this.title = title;
		this.oneLine = oneLine;
		this.icon = icon;
		this.content = content;
		this.emailForUt = emailForUt;
		this.androidRink = androidRink;
		this.iosRink = iosRink;
		this.webRink = webRink;
		this.views = views;
		this.status = status;
		this.writer = writer;
	}
	
	public void update(ProductUpdateRequest request) {
		this.title = request.getTitle();
		this.icon = request.getIcon();
		this.content = request.getContent();
		this.oneLine = request.getOneLine();
		this.emailForUt = request.getEmailForUt();
		this.androidRink = request.getAndroidRink();
		this.iosRink = request.getIosRink();
		this.webRink = request.getWebRink();
		this.status = request.getStatus();
	}
	
	public void increaseViews() {
		this.views++;
	}
	
	public void addProductPicture(ProductPicture productPicture) {
		if(productPictures.size() > 11) {
			return;
		}
		this.productPictures.add(productPicture);
		productPicture.setProduct(this);
	}
	
	public void addProductSkill(ProductSkill productSkill) {
		this.productSkills.add(productSkill);
		productSkill.setProduct(this);
	}
	
	public void updateProductSkills(List<Skill> skills) {
		if(CollectionUtils.isEmpty(skills)) {
			productSkills.clear();
			return;
		}
		
		// 기존 값 삭제
		productSkills.removeIf(productSkill -> skills.contains(productSkill.getSkill()));
		
		// 기존에 없는 값 추가
		skills.stream()
			.filter(skill -> productSkills.stream().noneMatch(productSkill -> productSkill.getSkill().equals(skill)))
			.map(ProductSkill::new)
			.forEach(this::addProductSkill);
	}
	
	public void updateProductPictures(List<ProductPicture> addProductPictures) {
		if(CollectionUtils.isEmpty(addProductPictures)) {
			productPictures.clear();
			return;
		}
		
		productPictures.removeIf(productPicture -> addProductPictures.contains(productPictures));
		
		addProductPictures.stream()
				.filter(addProductPicture -> productPictures.stream().noneMatch(productPicture -> productPicture.getId()==addProductPicture.getId()))
				.forEach(this::addProductPicture);
	}
}
