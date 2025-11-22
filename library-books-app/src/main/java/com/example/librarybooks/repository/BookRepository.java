package com.example.librarybooks.repository;

import com.example.librarybooks.model.Book;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookRepository {
    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @PostConstruct
    public void init() {
        save(new Book(null, "The Hobbit", "J.R.R. Tolkien"));
        save(new Book(null, "1984", "George Orwell"));
        save(new Book(null, "Clean Code", "Robert C. Martin"));
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(idCounter.incrementAndGet());
        }
        books.put(book.getId(), book);
        return book;
    }
}
