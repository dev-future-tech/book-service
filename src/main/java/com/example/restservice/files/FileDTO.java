package com.example.restservice.files;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileDTO {
    
    @JsonProperty("file_id")
    private Long fileId;

    @JsonProperty("name")
    private String filename;
    
    private String content;

    public Long getFileId() {
        return fileId;
    }
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    
}
