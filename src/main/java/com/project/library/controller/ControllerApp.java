package com.project.library.controller;

import com.project.library.entity.Book;
import com.project.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ControllerApp {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<Page<Book>> getAllBooks(Pageable pageable){
        return new ResponseEntity<>(bookService.getAllBooks(pageable), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable int id){
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<String> addNewBook(@RequestBody @Valid Book book){
        bookService.addNewBook(book);
        return new ResponseEntity<>("Book: " + book.getName() + " is added",HttpStatus.OK);
    }

    @PutMapping("/books")
    public ResponseEntity<String> updateBook(@RequestBody @Valid Book book){
        bookService.updateBook(book);
        return new ResponseEntity<>("Book: " + book.getName() + " is updated",HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book with id " + id + " is deleted");
    }
}
