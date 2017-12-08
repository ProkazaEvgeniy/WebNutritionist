package net.www.webnutritionist.repository.storage.queries;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import net.www.webnutritionist.entity.Category_;
import net.www.webnutritionist.entity.Producer_;
import net.www.webnutritionist.entity.Product;
import net.www.webnutritionist.entity.Product_;
import net.www.webnutritionist.form.SearchForm;

public class ProductSearchSpecification implements Specification<Product> {
	private SearchForm searchForm;

	public ProductSearchSpecification(SearchForm searchForm) {
		super();
		this.searchForm = searchForm;
	}

	@Override
	public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		String containsLikePattern = getContainsLikePattern(searchForm.getQuery());
		Predicate predicate = null;
		if(searchForm.isQueryNotEmpty()) {
			predicate = cb.or(
	                cb.like(cb.lower(root.get(Product_.name)), containsLikePattern),
	                cb.like(cb.lower(root.get(Product_.description)), containsLikePattern)
	        );
		}
		if(searchForm.isCategoriesNotEmpty()) {
			predicate = cb.and(
					predicate, 
					root.join(Product_.category).get(Category_.id).in(searchForm.getCategories()));
		}
		if(searchForm.isProducersNotEmpty()) {
			predicate = cb.and(
					predicate, 
					root.join(Product_.producer).get(Producer_.id).in(searchForm.getProducers()));
		}
        return predicate;
	}

	private String getContainsLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%";
        }
        else {
            return "%" + searchTerm.toLowerCase() + "%";
        }
    }
}
