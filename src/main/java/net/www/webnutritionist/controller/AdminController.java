package net.www.webnutritionist.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.www.webnutritionist.entity.Order;
import net.www.webnutritionist.exception.FormValidationException;
import net.www.webnutritionist.form.CategoryForm;
import net.www.webnutritionist.form.ProducerForm;
import net.www.webnutritionist.form.ProductForm;
import net.www.webnutritionist.service.AdminService;
import net.www.webnutritionist.service.OrderService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private OrderService orderService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping(value = "/admin/my-order", method = RequestMethod.GET)
	public String getMakeOrder(
			HttpServletRequest req, 
			Model model) {
		List<Order> orders = orderService.listOrderByProfileId(Long.parseLong(req.getParameter("id")));
		model.addAttribute("orders", orders);
		return "admin/my-orders-admin";
	}
	
	@RequestMapping(value = "/admin/order", method = RequestMethod.GET)
	public String getOrder(
			HttpServletRequest req, 
			Model model) {
		Order page = orderService.findOrderById(Long.parseLong(req.getParameter("id")));
		model.addAttribute("order", page);
		return "admin/order-admin";
	}
	
	@RequestMapping(value = "/admin/function", method = RequestMethod.GET)
	public String getAdminFuction() {
		return gotoAdminFunctionJSP();
	}

	private String gotoAdminFunctionJSP() {
		return "admin/function";
	}
	
	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public String getAdminHome(Model model) {
		model.addAttribute("profiles", adminService.findAllProfile());
		return gotoAdminHomeJSP();
	}

	private String gotoAdminHomeJSP() {
		return "admin/home";
	}
	
	@RequestMapping(value = "/admin/product", method = RequestMethod.GET)
	public String getAdminProduct(Model model) {
		model.addAttribute("productForm", new ProductForm());
		model.addAttribute("categories", adminService.findAllCategory());
		model.addAttribute("producers", adminService.findAllProducer());
		return gotoAdminProductJSP();
	}
	
	@RequestMapping(value = "/admin/save-product", method = RequestMethod.POST)
	public String setAdminSaveProduct(@Valid @ModelAttribute("productForm") ProductForm productForm,
			BindingResult bindingResult, @RequestParam("productPhoto") MultipartFile uploadPhoto) {
		if (bindingResult.hasErrors()) {
			return gotoAdminProductJSP();
		} else {
			try {
				adminService.createdProductData(productForm, uploadPhoto);
				return "redirect:/admin/product";
			} catch (FormValidationException e) {
				bindingResult.addError(e.buildFieldError("productForm"));
				return "admin/product";
			}
		}
	}
	
	private String gotoAdminProductJSP() {
		return "admin/product";
	}
	
	@RequestMapping(value = "/admin/producer-list", method = RequestMethod.GET)
	public String getProducerList(Model model){
		model.addAttribute("producers", adminService.findAllProducer());
		return gotoProducerListJSP();
	}
	
	private String gotoProducerListJSP() {
		return "admin/producer-list";
	}
	
	@RequestMapping(value = "/admin/producer-add", method = RequestMethod.GET)
	public String getProducerAdd(Model model){
		model.addAttribute("producerForm", new ProducerForm());
		return gotoProducerAddJSP();
	}
	
	@RequestMapping(value = "/admin/save-producer", method = RequestMethod.POST)
	public String saveProducer(@Valid @ModelAttribute("producerForm") ProducerForm producerForm,
			BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return gotoProducerAddJSP();
		} else {
			try {
				adminService.createdProducerData(producerForm);
				return "redirect:/admin/producer-add";
			} catch (FormValidationException e) {
				bindingResult.addError(e.buildFieldError("productForm"));
				return gotoProducerAddJSP();
			}
		}
	}
	
	@RequestMapping(value = { "/admin/deleteProducer" })
	public String deleteProducer(HttpServletRequest req) {
		adminService.deleteProducer(Integer.parseInt(req.getParameter("id")));
		return "redirect:/admin/producer-list";
	}
	
	private String gotoProducerAddJSP() {
		return "admin/producer-add";
	}
	
	@RequestMapping(value = "/admin/category-list", method = RequestMethod.GET)
	public String getCategoryList(Model model){
		model.addAttribute("categorys", adminService.findAllCategory());
		return gotoCategoryListJSP();
	}
	
	private String gotoCategoryListJSP() {
		return "admin/category-list";
	}
	
	@RequestMapping(value = "/admin/category-add", method = RequestMethod.GET)
	public String getCategoryAdd(Model model){
		model.addAttribute("categoryForm", new CategoryForm());
		return gotoCategoryAddJSP();
	}
	
	@RequestMapping(value = "/admin/save-category", method = RequestMethod.POST)
	public String saveCategory(@Valid @ModelAttribute("categoryForm") CategoryForm categoryForm,
			BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			return gotoCategoryAddJSP();
		} else {
			try {
				adminService.createdCategoryData(categoryForm);
				return "redirect:/admin/category-add";
			} catch (FormValidationException e) {
				bindingResult.addError(e.buildFieldError("categoryForm"));
				return gotoCategoryAddJSP();
			}
		}
	}
	
	@RequestMapping(value = { "/admin/deleteСategory" })
	public String deleteСategory(HttpServletRequest req) {
		adminService.deleteСategory(Integer.parseInt(req.getParameter("id")));
		return "redirect:/admin/producer-list";
	}
	
	private String gotoCategoryAddJSP() {
		return "admin/category-add";
	}
}