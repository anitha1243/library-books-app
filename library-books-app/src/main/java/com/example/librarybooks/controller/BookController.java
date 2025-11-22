package com.example.librarybooks.controller;

import com.example.librarybooks.model.Book;
import com.example.librarybooks.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Book> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book saved = repository.save(book);
        return ResponseEntity.created(URI.create("/api/books/" + saved.getId())).body(saved);
    }
}
