package com.example.library.service;

import com.example.library.common.PageResponse;
import com.example.library.dto.BorrowRequest;
import com.example.library.dto.ReturnRequest;
import com.example.library.entity.Borrowing;

public interface BorrowingService {
    Borrowing borrowBook(BorrowRequest borrowRequest);
    
    Borrowing returnBook(ReturnRequest returnRequest);
    
    PageResponse<Borrowing> getBorrowings(int page, int size, String status, Long userId, Long bookId);
    
    Borrowing extendBorrow(Long borrowId);
    
    Borrowing getBorrowingById(Long id);
}