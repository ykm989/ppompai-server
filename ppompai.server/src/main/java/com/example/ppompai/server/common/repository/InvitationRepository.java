package com.example.ppompai.server.common.repository;

import com.example.ppompai.server.common.domain.Invitation;
import com.example.ppompai.server.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByinvitee(User invitee);
}
