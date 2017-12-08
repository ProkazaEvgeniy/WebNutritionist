package net.www.webnutritionist.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import net.www.webnutritionist.entity.Product;

public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {

	Page<Product> findByNameLikeOrDescriptionLikeOrCategoryNameLikeOrProducerNameLike(
			String name, String description, String categoryName, String producerName, Pageable pageable);
	
}
