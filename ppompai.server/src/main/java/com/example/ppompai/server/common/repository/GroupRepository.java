package com.example.ppompai.server.common.repository;

import com.example.ppompai.server.common.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
