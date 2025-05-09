package com.semicolon.africa.service;

import com.semicolon.africa.data.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface DocumentService {

    Optional<Document> findById(int id);

    Optional<Document> uploadDocument(Document document, String filePath);

    boolean validateFormat(Document document);
}
