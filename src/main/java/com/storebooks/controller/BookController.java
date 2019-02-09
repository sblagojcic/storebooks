package com.storebooks.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.storebooks.model.Book;
import com.storebooks.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Book addBookPost(@RequestBody Book book){
		return bookService.save(book);
	}
	
	@RequestMapping(value="/add/image", method=RequestMethod.POST)
	public ResponseEntity upload(@RequestParam ("id")Long id, HttpServletResponse response, HttpServletRequest request ){
		try {
			Book book = bookService.findOne(id);
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			Iterator<String> i = multipartHttpServletRequest.getFileNames();
			MultipartFile multipartFile = multipartHttpServletRequest.getFile(i.next());
			String fileName = id+".png";
			
            Files.delete(Paths.get("src/main/resources/static/image/book/"+ fileName));
 
			byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book/"+ fileName)));
			stream.write(bytes);
			stream.close();
			
			return new ResponseEntity("Upload succes!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Upload failed!", HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(value="/bookList", method=RequestMethod.GET)
	public List<Book> getBookList(){
		return bookService.findAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Book getBook(@PathVariable("id")Long id){
		 Book book =bookService.findOne(id);
		 return book;
	}
	
	
	@RequestMapping (value="/update", method=RequestMethod.POST)
	public Book updateBook(@RequestBody Book book){
		return bookService.save(book);
	}
	
	@RequestMapping (value="/remove", method=RequestMethod.POST)
	public ResponseEntity remove(@RequestBody String id)throws IOException{
		 bookService.removeOne(Long.parseLong(id));
		 
         String fileName = id+".png";

         Files.delete(Paths.get("src/main/resources/static/image/book/"+ fileName));
         
		 return new ResponseEntity("Remove success!", HttpStatus.OK);
	}
	
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Book> searchByTitle(@RequestBody String keyword) {
        List<Book> bookList = bookService.searchByTitle(keyword);
        return bookList;
    }
	

    @RequestMapping(value = "/update/image", method = RequestMethod.POST)
    public ResponseEntity updateImage(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            Book book = bookService.findOne(id);
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = multipartRequest.getFileNames();
            MultipartFile multipartFile = multipartRequest.getFile(it.next());
            String fileName = id+".png";

            Files.delete(Paths.get("src/main/resources/static/image/book/"+ fileName));

            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book/"+ fileName)));
            stream.write(bytes);
            stream.close();
            return new ResponseEntity("Upload Success!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Upload Failed!", HttpStatus.BAD_REQUEST);
        }
    }
}
