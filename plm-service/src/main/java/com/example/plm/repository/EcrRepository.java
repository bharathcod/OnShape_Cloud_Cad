package com.example.plm.repository;

import com.example.plm.model.EcrRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcrRepository extends JpaRepository<EcrRequest, Long> {
}
