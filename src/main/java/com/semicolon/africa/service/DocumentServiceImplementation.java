package com.semicolon.africa.service;

import com.semicolon.africa.data.model.Document;
import com.semicolon.africa.data.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DocumentServiceImplementation implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    private boolean validateFormat(Document document) {
        String fileExtension = filePath.subString(filePath.)
    }
}
