package com.project.library.service;

import com.project.library.entity.Book;
import com.project.library.exception.ExistBookException;
import com.project.library.exception.NotFoundBookException;
import com.project.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Optional<Book> getBookById(int id) {
        if (bookRepository.findById(id).isEmpty()){
            throw new NotFoundBookException("Book with id: " + id + " is not founded");
        }
        return bookRepository.findById(id);
    }

    @Transactional
    @Override
    public void addNewBook(Book book) {
        if (bookRepository.findByName(book.getName()).isPresent()){
            throw new ExistBookException("Book with name: " + book.getName() + " already exist");
        }
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void updateBook(Book book) {
        if (!bookRepository.existsById(book.getId())){
            throw new NotFoundBookException("Book is not found");
        }
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBook(int id) {
        if (!bookRepository.existsById(id)){
            throw new NotFoundBookException("Book with id: " + id + " is not founded");
        }
        bookRepository.deleteById(id);
    }
}
