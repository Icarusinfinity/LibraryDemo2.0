package com.example.library.mapper;

import com.example.library.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {
    Book findById(Long id);
    
    List<Book> findAll();
    
    List<Book> findByPage(@Param("offset") int offset, @Param("size") int size);
    
    List<Book> findByPageAndKeyword(@Param("offset") int offset, @Param("size") int size, @Param("keyword") String keyword);
    
    List<Book> findByPageAndKeywordAndCategory(@Param("offset") int offset, @Param("size") int size, 
                                              @Param("keyword") String keyword, @Param("category") String category);
    
    List<Book> findByPageAndKeywordAndCategoryAndIsbn(@Param("offset") int offset, @Param("size") int size, 
                                                     @Param("keyword") String keyword, 
                                                     @Param("category") String category, 
                                                     @Param("isbn") String isbn);
    
    int countAll();
    
    int countByKeyword(@Param("keyword") String keyword);
    
    int countByKeywordAndCategory(@Param("keyword") String keyword, @Param("category") String category);
    
    int countByKeywordAndCategoryAndIsbn(@Param("keyword") String keyword, 
                                        @Param("category") String category, 
                                        @Param("isbn") String isbn);
    
    int insert(Book book);
    
    int update(Book book);
    
    int deleteById(Long id);
    
    List<String> findAllCategories();
}