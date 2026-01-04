package com.example.library.controller;

import com.example.library.common.ApiResponse;
import com.example.library.common.PageResponse;
import com.example.library.dto.BorrowRequest;
import com.example.library.dto.ReturnRequest;
import com.example.library.entity.Borrowing;
import com.example.library.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingController {
    
    @Autowired
    private BorrowingService borrowingService;
    
    @PostMapping("/borrow")
    public ApiResponse<Borrowing> borrowBook(@RequestBody BorrowRequest borrowRequest) {
        Borrowing borrowing = borrowingService.borrowBook(borrowRequest);
        return ApiResponse.success("借书成功", borrowing);
    }
    
    @PostMapping("/return")
    public ApiResponse<Borrowing> returnBook(@RequestBody ReturnRequest returnRequest) {
        Borrowing borrowing = borrowingService.returnBook(returnRequest);
        return ApiResponse.success("还书成功", borrowing);
    }
    
    @GetMapping("/borrowings")
    public ApiResponse<PageResponse<Borrowing>> getBorrowings(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long bookId) {
        
        PageResponse<Borrowing> borrowings = borrowingService.getBorrowings(page, size, status, userId, bookId);
        return ApiResponse.success(borrowings);
    }
    
    @PostMapping("/extend/{borrowId}")
    public ApiResponse<Borrowing> extendBorrow(@PathVariable Long borrowId) {
        Borrowing borrowing = borrowingService.extendBorrow(borrowId);
        return ApiResponse.success("续期成功", borrowing);
    }
}