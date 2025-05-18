package com.example.ppompai.server.common.repository;

import com.example.ppompai.server.common.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
}
