package com.springboard.backend.repository;

import com.springboard.backend.model.HtmlcodeDrive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HtmlcodeJpaRepository extends JpaRepository<HtmlcodeDrive, Long> {
}