package com.example.restservice.files;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

@WebMvcTest(FileController.class)
public class FileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    FileService fileService;


    @Test
    public void testSaveFiles() throws Exception {

        var inputValues = new FileCollectionDTO();
        List<FileDTO> files = new ArrayList<>();

        FileDTO file1 = new FileDTO();
        file1.setContent("This is a new file");
        file1.setFilename("new_file.txt");

        files.add(file1);


        inputValues.setFiles(files);

        var returnValues = new FileCollectionDTO();
        FileDTO file2 = new FileDTO();
        file2.setFileId(1L);
        file2.setFilename(file1.getFilename());
        file2.setContent(file1.getContent());

        List<FileDTO> returnFiles = new ArrayList<>();
        returnFiles.add(file2);
        returnValues.setFiles(returnFiles);

        when(fileService.saveFiles(Mockito.any(FileCollectionDTO.class))).thenReturn(returnValues);

        var mapper = new ObjectMapper();
        var strwrt = new StringWriter();
        mapper.writeValue(strwrt, inputValues);
        this.mockMvc.perform(
                post("/api/file").contentType(MediaType.APPLICATION_JSON).content(strwrt.toString()))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
