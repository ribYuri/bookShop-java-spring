package org.example.bookservice.service;

import org.example.bookservice.dto.BookDto;
import org.example.bookservice.entity.BookEntity;
import org.example.bookservice.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookEntity saveNewBook(BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();
        BeanUtils.copyProperties(bookDto, bookEntity);
        BookEntity save = bookRepository.save(bookEntity);
        return bookRepository.save(save);
    }
}
