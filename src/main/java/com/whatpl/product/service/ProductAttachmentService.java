package com.whatpl.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.whatpl.attachment.domain.Attachment;
import com.whatpl.attachment.service.AttachmentService;
import com.whatpl.product.domain.ProductPicture;

import io.jsonwebtoken.lang.Collections;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductAttachmentService {
	private final AttachmentService attachmentService;
	
	@Transactional
	public List<ProductPicture> uploadProductPicture(List<MultipartFile> picutres){
		if(CollectionUtils.isEmpty(picutres)) {
			return Collections.emptyList();
		}
		
		List<ProductPicture> productPictures = new ArrayList<>();
		picutres.forEach(picutre->{
			Long attachmentId = attachmentService.upload(picutre);
			Attachment attachment = attachmentService.findById(attachmentId);
			
			ProductPicture productPicutre = new ProductPicture(attachment);
			productPictures.add(productPicutre);
		});
		return productPictures;
	}
}
