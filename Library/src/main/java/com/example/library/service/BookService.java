package com.example.library.service;

import com.example.library.common.PageResponse;
import com.example.library.dto.Category;
import com.example.library.entity.Book;

import java.util.List;

public interface BookService {
    PageResponse<Book> getBooks(int page, int size, String keyword, String category, String isbn);
    
    Book getBookById(Long id);
    
    Book createBook(Book book);
    
    Book updateBook(Long id, Book book);
    
    void deleteBook(Long id);
    
    List<Category> getCategories();
}