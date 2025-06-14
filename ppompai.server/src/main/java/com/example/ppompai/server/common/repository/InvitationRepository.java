package com.example.ppompai.server.common.repository;

import com.example.ppompai.server.common.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
