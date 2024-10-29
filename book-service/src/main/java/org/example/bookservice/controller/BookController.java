package org.example.bookservice.controller;

import jakarta.validation.Valid;
import org.example.bookservice.dto.BookDto;
import org.example.bookservice.entity.BookEntity;
import org.example.bookservice.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/new")
    public ResponseEntity<BookEntity> createNewBook(@RequestBody @Valid BookDto bookDto) {
        return ResponseEntity.ok(bookService.saveNewBook(bookDto));
    }
}
