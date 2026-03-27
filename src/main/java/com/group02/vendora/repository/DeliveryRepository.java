package com.group02.vendora.repository;

import com.group02.vendora.model.Delivery;
import com.group02.vendora.model.DeliveryPerson;
import com.group02.vendora.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

    List<Delivery> findByAgentOrderByUpdatedAtDesc(DeliveryPerson agent);

    List<Delivery> findByStatusOrderByCreatedAtDesc(DeliveryStatus status);

    List<Delivery> findAllByOrderByCreatedAtDesc();
}