package com.app.bookstore.controllers;


import com.app.bookstore.models.Category;
import com.app.bookstore.services.BookService;
import com.app.bookstore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CategoryController {
    @Autowired
    public CategoryService categoryService;

    @Autowired
    BookService bookService;

    @PostMapping("/category")
    public void createCategory(@RequestBody Category category) {
        for (Category x : getAllCategories()) {
            if (x.getName().equals(category.getName())) {
                return;
            }
        }
        categoryService.createCategory(category);
    }

    @PutMapping("/category/{categoryId}")
    public void updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        categoryService.updateCategory(category, categoryId);
    }
    //shouldn't be able to delete a category with books tied to it without updating book category id
    @DeleteMapping("/category/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        System.out.println("Deleted category????");    }

    @GetMapping("/category/{categoryId}")
    public Category getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return categories;
    }

    @GetMapping("book/category/{bookId}")
    public Category getCategoryByBookId(@PathVariable Long bookId) {
        Long catId = bookService.getBookById(bookId).getCategoryId();
        return categoryService.getCategoryById(catId);
    }
}
