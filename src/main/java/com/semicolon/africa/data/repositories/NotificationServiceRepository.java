package com.semicolon.africa.data.repositories;

import com.semicolon.africa.data.model.NotificationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationServiceRepository extends JpaRepository<NotificationService, String> {
}
