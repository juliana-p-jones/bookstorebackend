package com.app.bookstore.services;

import com.app.bookstore.controllers.BookController;
import com.app.bookstore.models.Book;
import com.app.bookstore.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    Logger bookLog = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;

    public void createBook(Book book) {
        bookLog.info("===== CREATING NEW BOOK =====");
        bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        bookLog.info("===== GETTING ALL BOOKS =====");
        List<Book> listOfBooks = new ArrayList<Book>();
        bookRepository.findAll().forEach(listOfBooks::add);
        return listOfBooks;
    }

    public Book getBookById(Long id) {
        bookLog.info("===== GETTING BOOK BY BOOK ID =====");
        return bookRepository.findById(id).get();
    }

    public void updateBook(Book book, Long id) {
        bookLog.info("===== UPDATING BOOK =====");
        book.setId(id);
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookLog.info("===== DELETING BOOK BY ID =====");
        bookRepository.deleteById(id);
    }

    public List<Book> getAllBooksByCategory(Long categoryId) {
        bookLog.info("===== GETTING ALL BOOKS BY CATEGORY =====");

        List<Book> listOfBooks = getAllBooks()
                .stream()
                .filter(b -> b.getCategoryId() == categoryId)
                .collect(Collectors.toList());
        return listOfBooks;
    }

    public List<Book> getBooksBySearch(String keyword) {
        bookLog.info("===== GETTING BOOKS BY SEARCH =====");

        List<Book> listOfBooks = getAllBooks()
                .stream()
                .filter(b -> b.getName().contains(keyword))
                .collect(Collectors.toList());
        return listOfBooks;
    }
}
