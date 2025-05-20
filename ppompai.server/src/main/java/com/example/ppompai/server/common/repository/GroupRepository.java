package com.example.ppompai.server.common.repository;

import com.example.ppompai.server.common.domain.Group;
import com.example.ppompai.server.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByOwnerEmail(String ownerEmail);
    List<Group> findByParticipantsEmail(String userEmail);
}
