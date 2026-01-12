package com.project.library;

import com.project.library.entity.Book;
import com.project.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    public void testAddNewBook() throws Exception {
        String jsonContent = """
                {
                    "id": 1,
                    "name": "Vii"
                }
                """;
        doNothing().when(bookService).addNewBook(any(Book.class));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().string("Book: Vii is added"));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Page<Book> page = new PageImpl<>(new ArrayList<>());
        when(bookService.getAllBooks(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());

    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book(1, "Vii", null);
        when(bookService.getBookById(1)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Vii"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        String jsonContent = """
                {
                    "id": 1,
                    "name": "Vii"
                }
                """;
        doNothing().when(bookService).addNewBook(any(Book.class));

        mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().string("Book: Vii is updated"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(any(Integer.class));

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book with id 1 is deleted"));
    }
}
