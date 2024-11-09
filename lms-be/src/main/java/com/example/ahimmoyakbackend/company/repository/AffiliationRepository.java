package com.example.ahimmoyakbackend.company.repository;

import com.example.ahimmoyakbackend.company.entity.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {

    Optional<Affiliation> findByUserId(Long userId);
}
