package com.semicolon.africa.service;

import com.semicolon.africa.data.model.Document;
import com.semicolon.africa.data.repositories.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImplementation implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public Optional<Document> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<Document> uploadDocument(Document document, String filePath) {
        return Optional.empty();
    }

    @Override
    public boolean validateFormat(Document document) {
        return document.setIsDocumentValid(document.getFilePath());
    }
}
