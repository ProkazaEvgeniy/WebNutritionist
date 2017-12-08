package net.www.webnutritionist.service;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import net.www.webnutritionist.entity.Category;
import net.www.webnutritionist.entity.Producer;
import net.www.webnutritionist.entity.Product;
import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.form.CategoryForm;
import net.www.webnutritionist.form.ProducerForm;
import net.www.webnutritionist.form.ProductForm;
import net.www.webnutritionist.form.SearchForm;

public interface AdminService {

	@Nonnull List<Profile> findAllProfile();
	
	@Nonnull List<Product> findAllProducts();
	
	@Nonnull Page<Product> findAll(@Nonnull Pageable pageable);
	
	@Nonnull Page<Product> findBySearchQuery(@Nonnull SearchForm searchForm, @Nonnull Pageable pageable);
	
	@Nonnull long countBySearchQuery(@Nonnull SearchForm searchForm);
	
	@Nonnull Page<Product> findProductByCategoryUrl(@Nonnull String url, @Nonnull Pageable pageable);
	
	void createdProductData(@Nonnull ProductForm productForm, @Nonnull MultipartFile uploadPhoto);
	
	@Nonnull List<Producer> findAllProducer();
	
	void createdProducerData(@Nonnull ProducerForm producerForm);
	
	void deleteProducer(Integer id);
	
	@Nonnull List<Category> findAllCategory();
	
	void createdCategoryData(@Nonnull CategoryForm categoryForm);
	
	void deleteСategory(Integer id);
}
