package org.example.bookservice.controller;

import jakarta.validation.Valid;
import org.example.bookservice.domain.BookOrder;
import org.example.bookservice.dto.BookDto;
import org.example.bookservice.dto.BuyBookDto;
import org.example.bookservice.entity.BookEntity;
import org.example.bookservice.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/shop/calculate")
    public ResponseEntity<BookOrder> calculateOrderTotal(@RequestBody List<BuyBookDto> books) {
        return ResponseEntity.ok(bookService.generateBookOrder(books));
    }

    @PostMapping("/shop/buy")
    public ResponseEntity<String> submitOrder(@RequestBody @Valid BookOrder order) {
        return ResponseEntity.ok(bookService.submitBookOrder(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable(value = "id") Integer id) {
        Optional<BookEntity> bookById = bookService.getBookById(id);
        if (bookById.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(bookById.get());
    }

    @GetMapping()
    public ResponseEntity<List<BookEntity>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping("/new")
    public ResponseEntity<BookEntity> createNewBook(@RequestBody @Valid BookDto bookDto) {
        return ResponseEntity.ok(bookService.saveNewBook(bookDto));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BookEntity> updateBook(@PathVariable(value = "id") Integer id, @RequestBody @Valid BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookEntity> deleteBook(@PathVariable(value = "id") Integer id) {
        BookEntity deletedBook = bookService.deleteBook(id);
        if (deletedBook == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(deletedBook);
    }
}
