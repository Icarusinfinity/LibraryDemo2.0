package com.example.library.service.impl;

import com.example.library.common.PageResponse;
import com.example.library.dto.BorrowRequest;
import com.example.library.dto.ReturnRequest;
import com.example.library.entity.Book;
import com.example.library.entity.Borrowing;
import com.example.library.entity.User;
import com.example.library.mapper.BookMapper;
import com.example.library.mapper.BorrowingMapper;
import com.example.library.mapper.UserMapper;
import com.example.library.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BorrowingServiceImpl implements BorrowingService {
    
    @Autowired
    private BorrowingMapper borrowingMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private BookMapper bookMapper;
    
    @Override
    public Borrowing borrowBook(BorrowRequest borrowRequest) {
        // 检查用户是否存在
        User user = userMapper.findById(borrowRequest.getUserId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 检查图书是否存在且有库存
        Book book = bookMapper.findById(borrowRequest.getBookId());
        if (book == null || book.getStock() <= 0) {
            throw new RuntimeException("图书不存在或库存不足");
        }
        
        // 检查用户是否已借过此书
        List<Borrowing> userBorrowings = borrowingMapper.findByUserId(borrowRequest.getUserId());
        for (Borrowing borrowing : userBorrowings) {
            if (borrowing.getBookId().equals(borrowRequest.getBookId()) && 
                "borrowed".equals(borrowing.getStatus())) {
                throw new RuntimeException("用户已借过此书");
            }
        }
        
        // 创建借阅记录
        Borrowing borrowing = new Borrowing();
        borrowing.setUserId(borrowRequest.getUserId());
        borrowing.setBookId(borrowRequest.getBookId());
        borrowing.setBorrowDate(LocalDateTime.now());
        borrowing.setDueDate(LocalDateTime.now().plusDays(30)); // 30天借阅期
        borrowing.setStatus("borrowed");
        borrowing.setCreateTime(LocalDateTime.now());
        borrowing.setUpdateTime(LocalDateTime.now());
        
        borrowingMapper.insert(borrowing);
        
        // 更新图书库存
        book.setStock(book.getStock() - 1);
        book.setUpdateTime(LocalDateTime.now());
        bookMapper.update(book);
        
        return borrowing;
    }
    
    @Override
    public Borrowing returnBook(ReturnRequest returnRequest) {
        Borrowing borrowing = borrowingMapper.findById(returnRequest.getBorrowId());
        if (borrowing == null) {
            throw new RuntimeException("借阅记录不存在");
        }
        
        if (!"borrowed".equals(borrowing.getStatus())) {
            throw new RuntimeException("借阅记录状态不正确");
        }
        
        borrowing.setStatus("returned");
        borrowing.setReturnDate(LocalDateTime.now());
        borrowing.setUpdateTime(LocalDateTime.now());
        
        borrowingMapper.update(borrowing);
        
        // 更新图书库存
        Book book = bookMapper.findById(borrowing.getBookId());
        if (book != null) {
            book.setStock(book.getStock() + 1);
            book.setUpdateTime(LocalDateTime.now());
            bookMapper.update(book);
        }
        
        return borrowing;
    }
    
    @Override
    public PageResponse<Borrowing> getBorrowings(int page, int size, String status, Long userId, Long bookId) {
        int offset = (page - 1) * size;
        List<Borrowing> borrowingList;
        long total;
        
        if (StringUtils.hasText(status) && userId != null && bookId != null) {
            // 同时按状态、用户ID和图书ID筛选
            borrowingList = borrowingMapper.findByPageAndStatusAndBookId(offset, size, status, bookId);
            // 这里需要更精确的计数，先简化处理
            total = borrowingMapper.countByStatusAndBookId(status, bookId);
        } else if (StringUtils.hasText(status) && userId != null) {
            borrowingList = borrowingMapper.findByPageAndStatusAndUserId(offset, size, status, userId);
            total = borrowingMapper.countByStatusAndUserId(status, userId);
        } else if (StringUtils.hasText(status) && bookId != null) {
            borrowingList = borrowingMapper.findByPageAndStatusAndBookId(offset, size, status, bookId);
            total = borrowingMapper.countByStatusAndBookId(status, bookId);
        } else if (StringUtils.hasText(status)) {
            borrowingList = borrowingMapper.findByPageAndStatus(offset, size, status);
            total = borrowingMapper.countByStatus(status);
        } else if (userId != null) {
            borrowingList = borrowingMapper.findByPageAndUserId(offset, size, userId);
            total = borrowingMapper.countByUserId(userId);
        } else if (bookId != null) {
            borrowingList = borrowingMapper.findByPageAndBookId(offset, size, bookId);
            total = borrowingMapper.countByBookId(bookId);
        } else {
            borrowingList = borrowingMapper.findByPage(offset, size);
            total = borrowingMapper.countAll();
        }
        
        return new PageResponse<>(borrowingList, total, page, size);
    }
    
    @Override
    public Borrowing extendBorrow(Long borrowId) {
        Borrowing borrowing = borrowingMapper.findById(borrowId);
        if (borrowing == null) {
            throw new RuntimeException("借阅记录不存在");
        }
        
        if (!"borrowed".equals(borrowing.getStatus())) {
            throw new RuntimeException("借阅记录状态不正确，无法续期");
        }
        
        // 检查是否已逾期
        if (borrowing.getDueDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("借阅已逾期，无法续期");
        }
        
        // 续期7天
        LocalDateTime newDueDate = borrowing.getDueDate().plusDays(7);
        borrowing.setDueDate(newDueDate);
        borrowing.setUpdateTime(LocalDateTime.now());
        
        borrowingMapper.update(borrowing);
        
        return borrowing;
    }
    
    @Override
    public Borrowing getBorrowingById(Long id) {
        return borrowingMapper.findById(id);
    }
}