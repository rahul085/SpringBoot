package com.rahul.MultipleDbConnection.Controller;

import com.rahul.MultipleDbConnection.Entity.Book;
import com.rahul.MultipleDbConnection.Service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/Book")
public class BookController {

    private final BookService bookService;
    @PostMapping
    public ResponseEntity<Book> addBooks(@RequestBody Book book){
        return new ResponseEntity<>(bookService.addBooks(book), HttpStatus.OK);
    }
}
