package com.example.ahimmoyakbackend.institution.repository;

import com.example.ahimmoyakbackend.institution.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    boolean existsByBusinessNumber(String businessNumber);

    boolean existsByCertifiedNumber(String certifiedNumber);

}
