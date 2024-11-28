package com.example.restservice.files;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);
    private final JdbcTemplate jdbcTemplate;

    public FileService(JdbcTemplate _template) {
        this.jdbcTemplate = _template;
    }

    public FileCollectionDTO saveFiles(FileCollectionDTO files) {
        log.debug("Saving {} files", files.getFiles().size());

        List<FileDTO> savedFiles = files.getFiles()
                .stream()
                .map(file -> {
                    return saveFile(file);
                })
                .collect(Collectors.toList());

        FileCollectionDTO toReturn = new FileCollectionDTO();
        toReturn.setFiles(savedFiles);
        return toReturn;
    }

    private FileDTO saveFile(FileDTO file) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(cb -> {
            PreparedStatement stmt = cb
                    .prepareStatement("insert into files(file_name, content) values(?, ?) returning file_id",
                            new String[] { "file_id" });
            stmt.setString(1, file.getFilename());
            stmt.setString(2, file.getContent());
            return stmt;
        },
                keyHolder);
        file.setFileId(keyHolder.getKeyAs(Long.class));
        return file;
    }

}
