package com.driver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        // Your code goes here.
        BookController bc = new BookController();
        book.setId(getId());
        bookList.add(book);
        setBookList(bookList);
        this.setId(id++);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable() String bookId){
        int id = Integer.parseInt(bookId);
        for(Book book : bookList){
            if(book.getId() == id){
                return  new ResponseEntity<>(book, HttpStatus.OK);
            }
        }

        return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") String bookId){
        int id = Integer.parseInt(bookId);
        for(Book book : bookList){
            if(book.getId() == id){
                bookList.remove(book);
                return  new ResponseEntity<>("Record Deleted of id "+ bookId,HttpStatus.OK);
            }
        }
        return  new ResponseEntity<>("Book not found of id "+ bookId,HttpStatus.NOT_FOUND);
    }
    // get request /get-all-books
    // getAllBooks()
    @GetMapping("/get-all-books")
    public  ResponseEntity<List<Book>> getAllBooks(){

        return new ResponseEntity<>(getBookList() , HttpStatus.CREATED);
    }
    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping("/delete-all-books")
    public ResponseEntity<String> deleteAllBooks(){
        bookList.clear();
        return new ResponseEntity<>("Deleted all record",HttpStatus.CREATED);
    }
    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()
    @GetMapping("/get-books-by-author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author") String authorName){
        List<Book> books = new ArrayList<>();
        for(Book book : bookList){
            if(book.getAuthor().equals(authorName) ){
                books.add(book);
            }
        }
        return  new ResponseEntity<>(books,HttpStatus.OK);
    }
    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
    @GetMapping("/get-books-by-genre")
    public  ResponseEntity<List<Book>> getBooksByGenre(@RequestParam("genre") String genreName){
        List<Book> books = new ArrayList<>();
        for(Book book : bookList){
            if(book.getGenre().equals(genreName) ){
                books.add(book);
            }
        }
        return  new ResponseEntity<>(books,HttpStatus.OK);
    }
}
