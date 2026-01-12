package com.project.library.service;

import com.project.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface BookService {

    Page<Book> getAllBooks(Pageable pageable);

    Optional<Book> getBookById(int id);

    void addNewBook(Book book);

    void updateBook(Book book);

    void deleteBook(int id);
}
