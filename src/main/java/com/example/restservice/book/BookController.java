package com.example.restservice.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService _service) {
        this.bookService = _service;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooksByClassification(@RequestParam(required = false) String classification) {
        List<Book> books;

        if(classification == null) {
            log.debug("Getting all books...");
            books = this.bookService.getAllBooks();
        } else {
            log.info("Someone is getting a list of books based on the genre {}", classification);
            try {
                books = this.bookService.getBooksByClassification(classification);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return ResponseEntity.badRequest().build();
            }

        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
            Book book = this.bookService.getBookById(id);
            if(book != null) {
                return ResponseEntity.ok(book);
            }

            log.error("Problem retrieving book with id {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }



}
