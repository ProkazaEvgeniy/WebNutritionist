package net.www.webnutritionist.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import net.www.webnutritionist.entity.Category;
import net.www.webnutritionist.entity.Producer;
import net.www.webnutritionist.entity.Product;
import net.www.webnutritionist.model.UploadResult;
import net.www.webnutritionist.service.ImageProcessorService;

public abstract class AbstractCreateProductService {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ImageProcessorService imageProcessorService;

	protected Product createNewProduct(String name, Category category, Producer producer, BigDecimal price, String description,
			Date shelfLife, MultipartFile uploadPhoto) {
		UploadResult uploadResult = imageProcessorService.processNewProductPhoto(uploadPhoto);
		Product product = new Product();
		product.setName(name);
		product.setCategory(category);
		product.setProducer(producer);
		product.setPrice(price);
		product.setDescription(description);
		product.setShelfLife(shelfLife);
		product.setLargePhoto(uploadResult.getLargeUrl());
		return product;
	}
	
	protected Producer createNewProducer(String name, String productCount){
		Producer producer = new Producer();
		producer.setName(name);
		producer.setProductCount(Integer.parseInt(productCount));
		return producer;
	}
	
	protected Category createNewCategory(String name, String url, String productCount){
		Category category = new Category();
		category.setName(name);
		category.setUrl(url);
		category.setProductCount(Integer.parseInt(productCount));
		return category;
	}
}
