package com.example.library.mapper;

import com.example.library.entity.Borrowing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BorrowingMapper {
    Borrowing findById(Long id);
    
    List<Borrowing> findAll();
    
    List<Borrowing> findByPage(@Param("offset") int offset, @Param("size") int size);
    
    List<Borrowing> findByPageAndStatus(@Param("offset") int offset, @Param("size") int size, @Param("status") String status);
    
    List<Borrowing> findByPageAndUserId(@Param("offset") int offset, @Param("size") int size, @Param("userId") Long userId);
    
    List<Borrowing> findByPageAndBookId(@Param("offset") int offset, @Param("size") int size, @Param("bookId") Long bookId);
    
    List<Borrowing> findByPageAndStatusAndUserId(@Param("offset") int offset, @Param("size") int size, 
                                                @Param("status") String status, @Param("userId") Long userId);
    
    List<Borrowing> findByPageAndStatusAndBookId(@Param("offset") int offset, @Param("size") int size, 
                                                @Param("status") String status, @Param("bookId") Long bookId);
    
    List<Borrowing> findByUserId(Long userId);
    
    List<Borrowing> findByBookId(Long bookId);
    
    List<Borrowing> findByStatus(String status);
    
    int countAll();
    
    int countByStatus(@Param("status") String status);
    
    int countByUserId(@Param("userId") Long userId);
    
    int countByBookId(@Param("bookId") Long bookId);
    
    int countByStatusAndUserId(@Param("status") String status, @Param("userId") Long userId);
    
    int countByStatusAndBookId(@Param("status") String status, @Param("bookId") Long bookId);
    
    int insert(Borrowing borrowing);
    
    int update(Borrowing borrowing);
    
    int deleteById(Long id);
    
    List<Borrowing> findOverdueBooks();
}