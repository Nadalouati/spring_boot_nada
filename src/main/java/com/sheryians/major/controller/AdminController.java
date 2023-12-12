package com.sheryians.major.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.sheryians.major.dao.ProductDAO;
import com.sheryians.major.model.Category;
import com.sheryians.major.model.Product;
import com.sheryians.major.service.CategoryService;
import com.sheryians.major.service.ProductService;

@Controller
public class AdminController {
	
	public static String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;

	@GetMapping("/admin")
	public String adminHome(){
		return "adminHome";
	}
	@GetMapping("/admin/categories")
	public String getCat(Model model){
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}
	@GetMapping("/admin/categories/add")
	public String getCatAdd(Model model){
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}
	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category){
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id ,Model model) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if(category.isPresent()) {
			model.addAttribute("category", category.get());
			return "categoriesAdd";
		}else
			return "404";	
		}		
	//Product section
	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("products", productService.getAllProduct());
		return "products";	
	}
	@GetMapping("/admin/products/add")
	public String productAddGet(Model model) {
		model.addAttribute("productsDAO",new ProductDAO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";	
	}
	@PostMapping("/admin/products/add")
	public String ProductAddpost(@ModelAttribute("productDAO")ProductDAO productDAO,
								 @RequestParam("productImage")MultipartFile file,
								 @RequestParam("imgName")String imgName)throws IOException{
		Product product = new Product();
		product.setId(productDAO.getId());
		product.setName(productDAO.getName());
		product.setCategory(categoryService.getCategoryById(productDAO.getCategoryId()).get());
		product.setPrice(productDAO.getPrice());
		product.setWeight(productDAO.getWeight());
		product.setDescription(productDAO.getDescription());
		String imageUUID;
		if(!file.isEmpty()) {
			imageUUID=file.getOriginalFilename();
			java.nio.file.Path fileNameAndPath=java.nio.file.Paths.get(uploadDir,imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}else {
				imageUUID = imgName;
		}
		
		product.setImageName(imageUUID);
		productService.addProduct(product);
		return "redirect:/admin/products";
		}
		@GetMapping("/admin/product/delete/{id}")
		public String deleteProduct(@PathVariable Long id) {
			productService.removeProductById(id);
			return "redirect:/admin/products";
		}
		
		@GetMapping("/admin/product/update/{id}")
		public String updateProductGet(@PathVariable Long id,Model model) {
			Product product=productService.getProductById(id).get();	
			ProductDAO productDAO = new ProductDAO();
			productDAO.setId(product.getId());
			productDAO.setName(product.getName());
			
			productDAO.setCategoryId((product.getCategory().getId()));
			productDAO.setPrice(product.getPrice());
			productDAO.setWeight(product.getWeight());
			productDAO.setDescription(product.getDescription());
			productDAO.setImageName(product.getImageName());
			
			model.addAttribute("categories", categoryService.getAllCategory());
			model.addAttribute("productDAO", productDAO);
			
			return "redirect:/admin/products";
		}
		
}
