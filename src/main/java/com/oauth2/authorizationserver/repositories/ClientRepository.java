package com.oauth2.authorizationserver.repositories;

import com.oauth2.authorizationserver.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("""
            SELECT c from Client c WHERE c.id = :id
            """)
    Optional<Client> findById(Long id);

    @Query("""
            SELECT c from Client c WHERE c.clientId = :clientId
            """)
    Optional<Client> findByClientId(String clientId);
}
