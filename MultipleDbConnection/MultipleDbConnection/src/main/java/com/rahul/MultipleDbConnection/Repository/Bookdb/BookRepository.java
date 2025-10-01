package com.rahul.MultipleDbConnection.Repository.Bookdb;

import com.rahul.MultipleDbConnection.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {
}
