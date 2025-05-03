package ru.hogwarts.school.model;

import jakarta.persistence.*;

@Entity
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;

    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] data;
    @OneToOne
    private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
