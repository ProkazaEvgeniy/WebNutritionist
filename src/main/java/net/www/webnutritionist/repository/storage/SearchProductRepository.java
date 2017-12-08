package net.www.webnutritionist.repository.storage;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import net.www.webnutritionist.entity.Product;
import net.www.webnutritionist.form.SearchForm;

@NoRepositoryBean
public interface SearchProductRepository {

	List<Product> listProductsBySearchForm(SearchForm searchForm, Pageable pageable);

	int countProductsBySearchForm(SearchForm searchForm);
}