package com.Management.task.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Management.task.DTOs.CategoryDTO;
import com.Management.task.Models.Category;
import com.Management.task.Repository.CategoryRepository;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
    @Autowired
    private CategoryRepository categoryRepo;

	 @PostMapping("/create")
	    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO) {
	        Category category = new Category();
	        category.setName(categoryDTO.getName());
	        category.setDescription(categoryDTO.getDescription());
	        Category savedCategory = categoryRepo.save(category);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
	    }

	    @GetMapping("/all")
	    public ResponseEntity<List<Category>> getCategories() {
	        return ResponseEntity.ok(categoryRepo.findAll());
	    }
	    
	    @PutMapping("/update/{id}")
	    public ResponseEntity<Category> updateCategory(
	            @PathVariable Long id,
	            @RequestBody CategoryDTO categoryDTO) {

	        Category category = categoryRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

	        category.setName(categoryDTO.getName());
	        category.setDescription(categoryDTO.getDescription());

	        Category updatedCategory = categoryRepo.save(category);
	        return ResponseEntity.ok(updatedCategory);
	    }

}
