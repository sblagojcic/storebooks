package com.storebooks.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storebooks.model.Book;
import com.storebooks.repository.BookRepository;
import com.storebooks.service.BookService;

 
@Service
public class BookServiceImpl implements BookService {
	
	
	@Autowired
	private BookRepository bookRepository;

	
	public List<Book> findAll() {
		List<Book> bookList = (List<Book>) bookRepository.findAll();
		
		List<Book> activeBookList = new ArrayList<>();
		
		for(Book book: bookList){
			if(book.isActive()){
				activeBookList.add(book);
			}
		}
		return activeBookList;
	}

	
	public Book findOne(Long id) {
		return bookRepository.findOne(id);
	}

	
	public Book save(Book book) {
		return bookRepository.save(book);
	}
	
	  @Override
	    public List<Book> searchByTitle(String title) {
	        List<Book> bookList = bookRepository.findByTitleContaining(title);

	        List<Book> activeBookList = new ArrayList<>();
	        for (Book book: bookList){
	            if (book.isActive()){
	                activeBookList.add(book);
	            }
	        }
	        return activeBookList;
	    }

	
	public List<Book> blurrySearch(String keyboard) {
		List<Book> bookList = bookRepository.findByTitleContaining(keyboard);
		List<Book> activeBookList = new ArrayList<>();
		
		for(Book book: bookList){
			if(book.isActive()){
				activeBookList.add(book);
			}
		}
		return activeBookList; 
	}

	
	public void removeOne(Long id) {
		bookRepository.delete(id);
	}

}
