package org.example.bookservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.bookservice.domain.BookOrder;
import org.example.bookservice.dto.BookDto;
import org.example.bookservice.dto.BuyBookDto;
import org.example.bookservice.entity.BookEntity;
import org.example.bookservice.producer.OrderQueueProducer;
import org.example.bookservice.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final OrderQueueProducer orderQueue;

    public BookService(BookRepository bookRepository, OrderQueueProducer orderQueue) {
        this.bookRepository = bookRepository;
        this.orderQueue = orderQueue;
    }

    public BookOrder generateBookOrder(List<BuyBookDto> books) {
        BookOrder bookOrder = new BookOrder();
        bookOrder.setOrderTotal(getOrderTotal(books));
        bookOrder.setBooks(books);
        return bookOrder;
    }

    public String submitBookOrder(BookOrder bookOrder) {
        try {
            orderQueue.sendToQueue(bookOrder);
        } catch (JsonProcessingException e) {
            return "Error while trying to process order... " + e.getMessage();
        }
        return "The order is processing...";
    }

    public void updateBooksSoldQuantity(List<BuyBookDto> bookDtos) {
        for (BuyBookDto bookDto: bookDtos) {
            Optional<BookEntity> bookEntity = bookRepository.findById(bookDto.getBook().getId());
            if (bookEntity.isPresent()) {
                BookEntity book = bookEntity.get();
                book.setAmountSold(book.getAmountSold() + bookDto.getQuantity());
                bookRepository.save(book);
            }
        }
    }

    private Double getOrderTotal(List<BuyBookDto> books) {
        Double orderTotal = 0.0;
        for (BuyBookDto bookDto: books) {
            double bookTotal = bookDto.getBook().getPrice() * bookDto.getQuantity();
            orderTotal += bookTotal;
        }
        return orderTotal;
    }

    public Optional<BookEntity> getBookById(Integer id){
        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        return bookEntity;
    }

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public BookEntity saveNewBook(BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();
        BeanUtils.copyProperties(bookDto, bookEntity);
        BookEntity save = bookRepository.save(bookEntity);
        return bookRepository.save(save);
    }

    public BookEntity updateBook(Integer id, BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();
        BeanUtils.copyProperties(bookDto, bookEntity);
        bookEntity.setId(id);
        return bookRepository.save(bookEntity);
    }

    public BookEntity deleteBook(Integer id) {
        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        if (bookEntity.isPresent()) {
            bookRepository.delete(bookEntity.get());
            return bookEntity.get();
        }
        return null;
    }
}
