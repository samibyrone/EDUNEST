package com.semicolon.africa.data.repositories;

import com.semicolon.africa.data.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
}
