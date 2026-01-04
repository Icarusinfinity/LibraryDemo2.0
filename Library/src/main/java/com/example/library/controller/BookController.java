package com.example.library.controller;

import com.example.library.common.ApiResponse;
import com.example.library.common.PageResponse;
import com.example.library.dto.Category;
import com.example.library.entity.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping
    public ApiResponse<PageResponse<Book>> getBooks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String isbn) {
        
        PageResponse<Book> books = bookService.getBooks(page, size, keyword, category, isbn);
        return ApiResponse.success(books);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            return ApiResponse.success(book);
        } else {
            return ApiResponse.error(404, "图书不存在");
        }
    }
    
    @PostMapping
    public ApiResponse<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return ApiResponse.success("创建成功", createdBook);
    }
    
    @PutMapping("/{id}")
    public ApiResponse<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        if (updatedBook != null) {
            return ApiResponse.success("更新成功", updatedBook);
        } else {
            return ApiResponse.error(404, "图书不存在");
        }
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ApiResponse.success("删除成功", null);
    }
    
    @GetMapping("/categories")
    public ApiResponse<List<Category>> getCategories() {
        List<Category> categories = bookService.getCategories();
        return ApiResponse.success(categories);
    }
}