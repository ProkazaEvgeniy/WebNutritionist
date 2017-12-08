package net.www.webnutritionist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.www.webnutritionist.entity.Category;
import net.www.webnutritionist.entity.Producer;
import net.www.webnutritionist.entity.Product;
import net.www.webnutritionist.entity.Profile;
import net.www.webnutritionist.form.CategoryForm;
import net.www.webnutritionist.form.ProducerForm;
import net.www.webnutritionist.form.ProductForm;
import net.www.webnutritionist.form.SearchForm;
import net.www.webnutritionist.repository.storage.CategoryRepository;
import net.www.webnutritionist.repository.storage.ProducerRepository;
import net.www.webnutritionist.repository.storage.ProductRepository;
import net.www.webnutritionist.repository.storage.ProfileRepository;
import net.www.webnutritionist.repository.storage.queries.ProductSearchSpecification;
import net.www.webnutritionist.service.AdminService;

@Service
public class AdminServiceImpl extends AbstractCreateProductService implements AdminService{

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProducerRepository producerRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Profile> findAllProfile() {
		return profileRepository.findAll();
	}
	
	@Override
	public void createdProductData(ProductForm productForm, MultipartFile uploadPhoto) {
		Product product = createNewProduct(
				productForm.getName(), 
				productForm.getCategory(), 
				productForm.getProducer(),
				productForm.getPrice(),
				productForm.getDescription(),
				productForm.getShelfLife(),
				uploadPhoto);
		productRepository.save(product);
		productRepository.flush();
	}
	
	@Override
	public List<Producer> findAllProducer() {
		return producerRepository.findAll();
	}
	
	@Override
		public List<Category> findAllCategory() {
			return categoryRepository.findAll();
	}
	
	@Override
	public void createdProducerData(ProducerForm producerForm) {
		Producer producer = createNewProducer(producerForm.getName(), producerForm.getProductCount());
		producerRepository.save(producer);
		producerRepository.flush();
	}
	
	@Override
	@Transactional
	public void deleteProducer(Integer id) {
		producerRepository.delete(id);
	}
	
	@Override
	public void createdCategoryData(CategoryForm categoryForm) {
		Category category = createNewCategory(categoryForm.getName(), categoryForm.getUrl(), categoryForm.getProductCount());
		categoryRepository.save(category);
		categoryRepository.flush();
	}
	
	@Override
	@Transactional
	public void deleteСategory(Integer id) {
		categoryRepository.delete(id);
	}
	
	@Override
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}
	
	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}
	
	@Override
	public Page<Product> findBySearchQuery(SearchForm searchForm, Pageable pageable) {
		return productRepository.findAll(new ProductSearchSpecification(searchForm), pageable);
	}
	
	@Override
	public long countBySearchQuery(SearchForm searchForm) {
		return productRepository.count(new ProductSearchSpecification(searchForm));
	}
	
	@Override
	public Page<Product> findProductByCategoryUrl(String url, Pageable pageable) {
		return productRepository.findByCategoryUrl(url, pageable);
	}
	
}
