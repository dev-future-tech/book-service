package com.example.restservice.files;

import java.util.List;

public class FileCollectionDTO {

    private List<FileDTO> files;

    public List<FileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileDTO> files) {
        this.files = files;
    }

}