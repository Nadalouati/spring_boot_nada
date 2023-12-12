package com.sheryians.major.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sheryians.major.global.GlobalClass;
import com.sheryians.major.model.Product;
import com.sheryians.major.service.ProductService;

import org.springframework.ui.Model;

@Controller
public class CartController {
	@Autowired
	ProductService productService;
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable int id) {
		GlobalClass.cart.add(productService.getProductById(id).get());
		return "redirect:/shop";
	}
	@GetMapping("/cart")
	public String cartGet(Model model) {
		model.addAttribute("totalCount", GlobalClass.cart.size());
		model.addAttribute("total", GlobalClass.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cart", GlobalClass.cart);
		return "cart";
	}
	
	@GetMapping("/cart/removeItem/{index}")
	public String cartItemRemove(@PathVariable int index) {
		GlobalClass.cart.remove(index);
		return "redirect:/cart";
	}
	@GetMapping("/checkout")
	public String checkout(Model model) {
		model.addAttribute("total", GlobalClass.cart.stream().mapToDouble(Product::getPrice).sum());
		return "chekout";
	}

}
