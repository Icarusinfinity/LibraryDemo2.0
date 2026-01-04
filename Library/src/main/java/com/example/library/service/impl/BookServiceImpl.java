package com.example.library.service.impl;

import com.example.library.common.PageResponse;
import com.example.library.dto.Category;
import com.example.library.entity.Book;
import com.example.library.mapper.BookMapper;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    
    @Autowired
    private BookMapper bookMapper;
    
    @Override
    public PageResponse<Book> getBooks(int page, int size, String keyword, String category, String isbn) {
        int offset = (page - 1) * size;
        List<Book> bookList;
        long total;
        
        if (StringUtils.hasText(isbn) && StringUtils.hasText(category) && StringUtils.hasText(keyword)) {
            bookList = bookMapper.findByPageAndKeywordAndCategoryAndIsbn(offset, size, keyword, category, isbn);
            total = bookMapper.countByKeywordAndCategoryAndIsbn(keyword, category, isbn);
        } else if (StringUtils.hasText(category) && StringUtils.hasText(keyword)) {
            bookList = bookMapper.findByPageAndKeywordAndCategory(offset, size, keyword, category);
            total = bookMapper.countByKeywordAndCategory(keyword, category);
        } else if (StringUtils.hasText(keyword)) {
            bookList = bookMapper.findByPageAndKeyword(offset, size, keyword);
            total = bookMapper.countByKeyword(keyword);
        } else {
            bookList = bookMapper.findByPage(offset, size);
            total = bookMapper.countAll();
        }
        
        return new PageResponse<>(bookList, total, page, size);
    }
    
    @Override
    public Book getBookById(Long id) {
        return bookMapper.findById(id);
    }
    
    @Override
    public Book createBook(Book book) {
        book.setCreateTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());
        bookMapper.insert(book);
        return book;
    }
    
    @Override
    public Book updateBook(Long id, Book book) {
        Book existingBook = bookMapper.findById(id);
        if (existingBook != null) {
            book.setId(id);
            book.setUpdateTime(LocalDateTime.now());
            bookMapper.update(book);
            return book;
        }
        return null;
    }
    
    @Override
    public void deleteBook(Long id) {
        bookMapper.deleteById(id);
    }
    
    @Override
    public List<Category> getCategories() {
        List<String> categoryList = bookMapper.findAllCategories();
        return categoryList.stream()
                .map(category -> {
                    Category cat = new Category();
                    cat.setValue(category);
                    cat.setLabel(category);
                    return cat;
                })
                .collect(Collectors.toList());
    }
}