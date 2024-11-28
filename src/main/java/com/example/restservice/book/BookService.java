package com.example.restservice.book;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookService {

    private final JdbcTemplate jdbcTemplate;

    BookService(JdbcTemplate _template) {
        this.jdbcTemplate = _template;
    }

    List<Book> getBooksByClassification(String classification) throws SQLException {
        String select = "select * from books where genre = ?";

        return this.jdbcTemplate.query(
                (psc) -> {
                    PreparedStatement pstmt = psc.prepareStatement(select);
                    pstmt.setString(1, classification);
                    return pstmt;
                },
                (rs) -> {
                    List<Book> books = new ArrayList<>();
                    while(rs.next()) {
                        Book book = new Book();
                        book.setBookId(rs.getLong("book_id"));
                        book.setTitle(rs.getString("title"));
                        book.setAuthor(rs.getString("author"));
                        book.setGenre(rs.getString("genre"));
                        book.setPublisher(rs.getString("publisher"));
                        books.add(book);
                    }
                    return books;
                });
    }


    Book getBookById(Long id) {
        String query = "select book_id, title, author, genre, publisher from books where book_id = ?";
        return this.jdbcTemplate.queryForObject(
                query,
                new Object[]{id},
                new int[]{Types.BIGINT},
                (rs, rowNum) -> {
                    Book book = new Book();
                    book.setBookId(rs.getLong("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setGenre(rs.getString("genre"));
                    book.setPublisher(rs.getString("publisher"));
                    return book;
                }
        );
    }

    public List<Book> getAllBooks() {
        var query = "select * from books";

        return this.jdbcTemplate.query(
                query,
                (rse) -> {
                    List<Book> books = new ArrayList<>();
                    while(rse.next()) {
                        Book book = new Book();
                        book.setBookId(rse.getLong("book_id"));
                        book.setTitle(rse.getString("title"));
                        book.setAuthor(rse.getString("author"));
                        book.setGenre(rse.getString("genre"));
                        book.setPublisher(rse.getString("publisher"));
                        books.add(book);
                    }
                    return books;
                }
                );
    }
}
