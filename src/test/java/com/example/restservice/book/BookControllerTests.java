package com.example.restservice.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getBookByIdTest() throws Exception {
        this.mockMvc.perform(get("/api/book/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.book_id").value("1"));
    }

    @Test
    public void getBooksByGenre() throws Exception {
        this.mockMvc.perform(get("/api/book").queryParam("classification", "science"))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(14)));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        this.mockMvc.perform(get("/api/book"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
