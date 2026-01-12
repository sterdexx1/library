package com.project.library;

import com.project.library.entity.Book;
import com.project.library.exception.NotFoundBookException;
import com.project.library.repository.BookRepository;
import com.project.library.service.BookService;
import com.project.library.service.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService = new BookServiceImpl();


    @Test
    public void testFindExistBookById() {
        Book book = new Book(1, "Vii", null);

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        Optional<Book> result = bookService.getBookById(1);
        assertEquals("Vii", result.get().getName());
    }

    @Test
    public void testFindNonExistingBookById(){
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundBookException.class, () -> bookService.getBookById(1));
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveBook() {
        Book book = new Book(1, "Vii",null);
        bookService.addNewBook(book);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testDeleteExistBook() {
        when(bookRepository.existsById(1)).thenReturn(true);

        bookService.deleteBook(1);
        verify(bookRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteNonExistingBook(){
        when(bookRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundBookException.class, () -> bookService.deleteBook(1));
        verify(bookRepository, never()).deleteById(1);
    }

    @Test
    public void testUpdateExistBook(){
        when(bookRepository.existsById(1)).thenReturn(true);

        Book newBook = new Book(1, "Vii", null);

        bookService.updateBook(newBook);
        verify(bookRepository, times(1)).save(newBook);
    }

    @Test
    public void testUpdateNonExistingBook(){
        when(bookRepository.existsById(1)).thenReturn(false);
        assertThrows(NotFoundBookException.class, () ->
                bookService.updateBook(new Book(1, "Vii",null)));
        verify(bookRepository, never()).save(any(Book.class));
    }
}
