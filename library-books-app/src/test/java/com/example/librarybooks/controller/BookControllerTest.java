package com.example.librarybooks.controller;

import com.example.librarybooks.model.Book;
import com.example.librarybooks.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository repository;

    @Test
    void getAllReturnsBooks() throws Exception {
        List<Book> books = List.of(new Book(1L, "The Hobbit", "J.R.R. Tolkien"));
        when(repository.findAll()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("The Hobbit"));
    }

    @Test
    void getByIdFound() throws Exception {
        Book b = new Book(2L, "1984", "George Orwell");
        when(repository.findById(2L)).thenReturn(Optional.of(b));

        mockMvc.perform(get("/api/books/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("1984"));
    }

    @Test
    void getByIdNotFound() throws Exception {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void postCreatesBook() throws Exception {
        when(repository.save(any())).thenAnswer(invocation -> {
            Book arg = invocation.getArgument(0);
            arg.setId(4L);
            return arg;
        });

        String json = "{\"title\":\"My Book\",\"author\":\"AK\"}";

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/books/4"))
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.title").value("My Book"));
    }
}
