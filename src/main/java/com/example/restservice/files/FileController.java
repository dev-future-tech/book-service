package com.example.restservice.files;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final Logger log = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;

    public FileController(FileService _service) {
        this.fileService = _service;
    }

    @PostMapping()
    public ResponseEntity<FileCollectionDTO> saveFiles(@RequestBody FileCollectionDTO files) {
        log.debug("Saving files...");

        log.info("Saving {} files", files.getFiles().size());
        FileCollectionDTO saved = this.fileService.saveFiles(files);

        return ResponseEntity.created(URI.create("/api/files/")).body(saved);
    }
}
