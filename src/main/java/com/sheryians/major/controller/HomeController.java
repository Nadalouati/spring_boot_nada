package com.sheryians.major.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sheryians.major.global.GlobalClass;
import com.sheryians.major.service.CategoryService;
import com.sheryians.major.service.ProductService;


@Controller
public class HomeController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping({"/","/home"})
	public String home(Model model) {
		return "index";
		
	}
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("totalCount", GlobalClass.cart.size());
		model.addAttribute("products", productService.getAllProduct());
		return "shop";
	}
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model,@PathVariable int id) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("totalCount", GlobalClass.cart.size());
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		return "shop";
	}
	@GetMapping("/shop/viewproduct/{id}")
	public String viewproduct(Model model,@PathVariable int id) {
		model.addAttribute("product", productService.getProductById(id).get());
		model.addAttribute("totalCount", GlobalClass.cart.size());
		return "viewproduct";
	}

}
