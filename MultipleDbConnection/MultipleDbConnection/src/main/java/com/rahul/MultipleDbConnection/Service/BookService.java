package com.rahul.MultipleDbConnection.Service;

import com.rahul.MultipleDbConnection.Entity.Book;
import com.rahul.MultipleDbConnection.Repository.Bookdb.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book addBooks(Book book){
      return  bookRepository.save(book);
    }
}
