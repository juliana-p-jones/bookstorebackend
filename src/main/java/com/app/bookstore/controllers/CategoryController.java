package com.app.bookstore.controllers;


import com.app.bookstore.exceptionhandling.CodeMessage;
import com.app.bookstore.exceptionhandling.CodeMessageData;
import com.app.bookstore.models.Category;
import com.app.bookstore.services.BookService;
import com.app.bookstore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        categoryService.createCategory(category);
    }

//    @PostMapping("/category")
//    public void createCategory(@RequestBody Category category) {
//        for (Category x : getAllCategories()) {
//            if (x.getName().equals(category.getName())) {
//                return;
//            }
//        }
//        categoryService.createCategory(category);
//    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        categoryService.updateCategory(category, categoryId);
        if(categoryService.categoryCheck(categoryId)){
            categoryService.updateCategory(category, categoryId);
            CodeMessage response = new CodeMessage(201, "Category Updated");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CodeMessage exception = new CodeMessage(404, "Error: Category Not Found");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }

    //shouldn't be able to delete a category with books tied to it without updating book category id
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        System.out.println("Deleted category????");
        if(!categoryService.categoryCheck(categoryId)){
            CodeMessage exception = new CodeMessage("This Id Does Not Exist In Category");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId) {
        if(categoryService.categoryCheck(categoryId) == false){
            CodeMessage error = new CodeMessage(404, "Error Fetching Category");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        CodeMessageData response = new CodeMessageData(200, "Success", categoryService.getCategoryById(categoryId));
        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if(categories.isEmpty()){
            CodeMessage error = new CodeMessage(404, "Error Fetching Categories");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        CodeMessageData response = new CodeMessageData(200, "Success", categories);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("book/category/{bookId}")
    public ResponseEntity<?> getCategoryByBookId(@PathVariable Long bookId) {
        Long catId = bookService.getBookById(bookId).get().getCategoryId();
        if(bookService.bookCheck(bookId) == false){
            CodeMessage error = new CodeMessage(404, "Error Fetching Categories");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CodeMessageData response = new CodeMessageData(200, "Success", categoryService.getCategoryById(catId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
