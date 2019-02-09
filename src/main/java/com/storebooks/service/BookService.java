package com.storebooks.service;

import java.util.List;

import com.storebooks.model.Book;

public interface BookService {

	List<Book> findAll();
	
	Book findOne(Long id);
	
	Book save(Book book);
	 
	List<Book> blurrySearch(String title);
	
    List<Book> searchByTitle(String title);
	
	void removeOne(Long id);
}
