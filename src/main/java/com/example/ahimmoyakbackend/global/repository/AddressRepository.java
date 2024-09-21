package com.example.ahimmoyakbackend.global.repository;

import com.example.ahimmoyakbackend.global.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser_Id(Long id);
}