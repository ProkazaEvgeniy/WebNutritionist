package net.www.webnutritionist.controller;

import static net.www.webnutritionist.Constants.UI.MAX_PRODUCTS_PER_PAGE;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.www.webnutritionist.entity.Product;
import net.www.webnutritionist.form.SearchForm;
import net.www.webnutritionist.service.AdminService;

@Controller
public class ShopController {

	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value = { "/products" })
	public String getProduct(Model model) {
		Page<Product> page = adminService.findAll(new PageRequest(0, MAX_PRODUCTS_PER_PAGE, new Sort("id")));
		model.addAttribute("products", page.getContent());
		model.addAttribute("page", page);
		return gotoProductJSP(model);
	}
	
	private String gotoProductJSP(Model model){
		model.addAttribute("categorys", adminService.findAllCategory());
		model.addAttribute("producers", adminService.findAllProducer());
		return "product";
	}
	
	@RequestMapping(value = { "/searchProduts" }, method = RequestMethod.GET)
	public String searchResultProduct(@ModelAttribute SearchForm searchForm,
			Model model, 
			@PageableDefault(size=MAX_PRODUCTS_PER_PAGE) Pageable pageable){
		if(StringUtils.isBlank(searchForm.getQuery())){
			return "redirect:/product";
		} else {
			Page<Product> page = adminService.findBySearchQuery(searchForm, pageable);
			model.addAttribute("page", page);
			model.addAttribute("products", page.getContent());
			model.addAttribute("productCount", adminService.countBySearchQuery(searchForm));
			model.addAttribute("categorys", adminService.findAllCategory());
			model.addAttribute("producers", adminService.findAllProducer());
			return gotoSearchResultProduct();
		}
		
	}
	
	private String gotoSearchResultProduct(){
		return "search-result-product";
	}
	
	@RequestMapping(value = { "/fragment/more/searchProduts" }, method = RequestMethod.GET)
	public String searchResultProductMore(
			@ModelAttribute SearchForm searchForm,
			Model model, 
			@PageableDefault(size=MAX_PRODUCTS_PER_PAGE) Pageable pageable){
		Page<Product> page = adminService.findBySearchQuery(searchForm, pageable);
		model.addAttribute("page", page);
		model.addAttribute("products", page.getContent());
		return gotoProductListJSP();
	}
	
	@RequestMapping(value = { "/products/{url}" }, method = RequestMethod.GET)
	public String getProductsByCategory(
			@PathVariable String url, 
			Model model, 
			@PageableDefault(size=MAX_PRODUCTS_PER_PAGE) @SortDefault(sort="id") Pageable pageable) throws UnsupportedEncodingException {
		Page<Product> page = adminService.findProductByCategoryUrl(url, pageable);
		model.addAttribute("page", page);
		model.addAttribute("products", page.getContent());
		return gotoProductJSP(model);
	}
	
	@RequestMapping(value = "/fragment/more/products", method = RequestMethod.GET)
	public String moreProducts(
			Model model,
			@PageableDefault(size=MAX_PRODUCTS_PER_PAGE) @SortDefault(sort="id") Pageable pageable) throws UnsupportedEncodingException {
		Page<Product> page = adminService.findAll(pageable);
		model.addAttribute("page", page);
		model.addAttribute("products", page.getContent());
		return gotoProductListJSP();
	}
	
	@RequestMapping(value = { "/fragment/more/products/{url}" }, method = RequestMethod.GET)
	public String getProductsByCategoryMore(
			@PathVariable String url, 
			Model model, 
			@PageableDefault(size=MAX_PRODUCTS_PER_PAGE) @SortDefault(sort="id") Pageable pageable){
		Page<Product> page = adminService.findProductByCategoryUrl(url, pageable);
		model.addAttribute("page", page);
		model.addAttribute("products", page.getContent());
		return gotoProductListJSP();
	}

	private String gotoProductListJSP() {
		return "fragment/product-list";
	}
	
	
	
}
