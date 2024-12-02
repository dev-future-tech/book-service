package com.example.restservice.book;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Testcontainers
public class BookControllerTests {

//    @Container
//    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17-alpine");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    BookService service;

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//        registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
    }

    @Test
    public void getBookByIdTest() throws Exception {
        var book = new Book();
        book.setBookId(123124L);
        book.setTitle("Wind in the Willows");
        book.setAuthor("Mary Whisp");
        book.setGenre("Fiction");
        book.setPublisher("Words in the Woods");

        when(service.getBookById(Mockito.anyLong())).thenReturn(book);
        this.mockMvc.perform(get("/api/book/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.book_id").value("123124"));
    }

    @Test
    public void getBooksByGenre() throws Exception {
        var books = getSomeBooksForTesting();

        when(service.getBooksByClassification(Mockito.anyString())).thenReturn(books);
        this.mockMvc.perform(get("/api/book").queryParam("classification", "science"))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        this.mockMvc.perform(get("/api/book"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private List<Book> getSomeBooksForTesting() {
        var book1 = new Book();
        book1.setBookId(123124L);
        book1.setTitle("Wind in the Willows");
        book1.setAuthor("Mary Whisp");
        book1.setGenre("science");
        book1.setPublisher("Words in the Woods");

        var book2 = new Book();
        book2.setBookId(123125L);
        book2.setTitle("Wind in the Willows");
        book2.setAuthor("Mary Whisp");
        book2.setGenre("science");
        book2.setPublisher("Words in the Woods");

        var book3 = new Book();
        book3.setBookId(123126L);
        book3.setTitle("Wind in the Willows");
        book3.setAuthor("Mary Whisp");
        book3.setGenre("science");
        book3.setPublisher("Words in the Woods");

        var books = new ArrayList<Book>();
        books.add(book1);
        books.add(book2);
        books.add(book3);

        return books;
    }
}
