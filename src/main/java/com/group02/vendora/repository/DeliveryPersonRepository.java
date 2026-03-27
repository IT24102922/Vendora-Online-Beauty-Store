package com.group02.vendora.repository;

import com.group02.vendora.model.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {

    boolean existsByEmail(String email);

    Optional<DeliveryPerson> findByEmail(String email);
}
