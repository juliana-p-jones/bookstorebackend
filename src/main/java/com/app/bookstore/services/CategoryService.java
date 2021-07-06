package com.app.bookstore.services;

import com.app.bookstore.controllers.BookController;
import com.app.bookstore.controllers.CategoryController;
import com.app.bookstore.models.Category;
import com.app.bookstore.repositories.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    Logger categoryLog = LoggerFactory.getLogger(CategoryController.class);


    @Autowired
    CategoryRepository categoryRepository;

    public void createCategory(Category category) {
        categoryLog.info("===== CREATING NEW CATEGORY =====");
        categoryRepository.save(category);
    }

    public void updateCategory(Category category, Long categoryId) {
        categoryLog.info("===== UPDATING CATEGORY =====");
        category.setId(categoryId);
        categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        categoryLog.info("===== DELETING CATEGORY =====");
        categoryRepository.deleteById(categoryId);
    }

    public Category getCategoryById(Long categoryId) {
        categoryLog.info("===== GETTING CATEGORY BY CATEGORY ID =====");
        return categoryRepository.findById(categoryId).get();
    }

    public List<Category> getAllCategories() {
        categoryLog.info("===== GETTING ALL CATEGORIES =====");
        List<Category> listOfCategories = new ArrayList<Category>();
        categoryRepository.findAll().forEach(listOfCategories::add);
        return listOfCategories;
    }

}
