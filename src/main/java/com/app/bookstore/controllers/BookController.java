package com.app.bookstore.controllers;


import com.app.bookstore.models.Book;
import com.app.bookstore.models.Category;
import com.app.bookstore.repositories.CategoryRepository;
import com.app.bookstore.services.BookService;
import com.app.bookstore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping(path = "/bookstoreapp")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/book")
    public void createBook(@RequestBody Book book) {
        bookService.createBook(book);
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @PutMapping("/book/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody Book book){
        bookService.updateBook(book, id);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return books;
    }

    @GetMapping("/books/category/{categoryId}")
    public List<Book> getAllBooksByCategory(@PathVariable Long categoryId){
        List<Book> books = bookService.getAllBooksByCategory(categoryId);
        return books;
    }

    @GetMapping("/books/search/{keyword}")
    public List<Book> getBooksBySearch(@PathVariable String keyword){
        return bookService.getBooksBySearch(keyword);
    }
}
