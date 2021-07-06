package com.app.bookstore.controllers;


import com.app.bookstore.exceptionhandling.CodeMessage;
import com.app.bookstore.exceptionhandling.CodeMessageData;
import com.app.bookstore.models.Book;
import com.app.bookstore.models.Category;
import com.app.bookstore.repositories.CategoryRepository;
import com.app.bookstore.services.BookService;
import com.app.bookstore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        try {
            CodeMessageData response = new CodeMessageData(200, "New Book Created",  bookService.createBook(book));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            CodeMessage error = new CodeMessage(404, "Error creating customer");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id).orElse(null);
        if (book == null) {
            CodeMessage error = new CodeMessage(404, "Error Fetching Book");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CodeMessageData response = new CodeMessageData(200, "Success", book);
        return new ResponseEntity<> (response, HttpStatus.OK);
    }


    @PutMapping("/book/{id}")
    public ResponseEntity<?>  updateBook(@PathVariable Long id, @RequestBody Book book){
        if(!bookService.bookCheck(id)){
            CodeMessage exception = new CodeMessage("Book ID Does Not Exist");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        bookService.updateBook(book, id);
        CodeMessage response = new CodeMessage(202, "Accepted Book Modification");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        if(!bookService.bookCheck(id)){
            CodeMessage exception = new CodeMessage("This ID Does Not Exist in Book");
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if(books.isEmpty()){
            CodeMessage error = new CodeMessage(404, "Error Fetching Books");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        CodeMessageData response = new CodeMessageData(200, "Success", books);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/books/category/{categoryId}")
    public ResponseEntity<?> getAllBooksByCategory(@PathVariable Long categoryId){
        Iterable<Book> books = bookService.getAllBooksByCategory(categoryId);
        if (books.iterator().hasNext()) {
            CodeMessageData response = new CodeMessageData(200, "Success", books);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        CodeMessage exception = new CodeMessage(404,"Error Fetching Books");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/books/search/{keyword}")
    public ResponseEntity<?> getBooksBySearch(@PathVariable String keyword){
        Iterable<Book> bookQueryList = bookService.getBooksBySearch(keyword);
        if(bookQueryList.iterator().hasNext()) {
            CodeMessageData response = new CodeMessageData(200, "Success", bookQueryList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        CodeMessage exception = new CodeMessage(200, "No Matches Found");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
}
