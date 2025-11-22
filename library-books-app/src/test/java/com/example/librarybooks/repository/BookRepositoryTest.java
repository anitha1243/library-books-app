package com.example.librarybooks.repository;

import com.example.librarybooks.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    private BookRepository repository;

    @BeforeEach
    void setUp() {
        repository = new BookRepository();
        // ensure seeded data is present as in @PostConstruct
        repository.init();
    }

    @Test
    void saveAssignsIdAndCanBeRetrieved() {
        Book book = new Book(null, "Test Title", "Tester");
        Book saved = repository.save(book);

        assertNotNull(saved.getId(), "Saved book should have an id assigned");

        Optional<Book> found = repository.findById(saved.getId());
        assertTrue(found.isPresent(), "Repository should return the saved book by id");
        assertEquals("Test Title", found.get().getTitle());
        assertEquals("Tester", found.get().getAuthor());
    }

    @Test
    void findAllContainsSeededBooksAndNewOne() {
        List<Book> before = repository.findAll();
        int initialSize = before.size();

        repository.save(new Book(null, "Another", "Author"));

        List<Book> after = repository.findAll();
        assertEquals(initialSize + 1, after.size(), "findAll should reflect newly saved book");
    }

    @Test
    void findByIdReturnsEmptyForMissing() {
        Optional<Book> missing = repository.findById(99999L);
        assertTrue(missing.isEmpty(), "findById should return empty Optional for unknown id");
    }
}
